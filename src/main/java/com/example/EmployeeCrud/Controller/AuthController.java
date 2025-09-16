package com.example.EmployeeCrud.Controller;

import com.example.EmployeeCrud.Payload.Request.LoginRequest;
import com.example.EmployeeCrud.Payload.Request.SignupRequest;
import com.example.EmployeeCrud.Payload.Request.TokenRefreshRequest;
import com.example.EmployeeCrud.Payload.Response.JwtResponse;
import com.example.EmployeeCrud.Payload.Response.MessageResponse;
import com.example.EmployeeCrud.Payload.Response.TokenRefreshResponse;
import com.example.EmployeeCrud.Models.User;
import com.example.EmployeeCrud.Models.ERole;
import com.example.EmployeeCrud.Models.RefreshToken;
import com.example.EmployeeCrud.Repository.UserRepository;
import com.example.EmployeeCrud.Security.Jwt.JwtUtils;
import com.example.EmployeeCrud.Security.Services.UserDetailsImpl;
import com.example.EmployeeCrud.Security.Services.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(),
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<ERole> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(ERole.USER);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equalsIgnoreCase(role)) {
                    roles.add(ERole.ADMIN);
                } else {
                    roles.add(ERole.USER);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> jwtUtils.generateTokenFromUsername(user.getUsername()))
                .map(token -> new TokenRefreshResponse(token, requestRefreshToken))
                .<ResponseEntity<?>>map(ResponseEntity::ok)   // wrap success
                .orElseGet(() -> ResponseEntity.badRequest()
                        .body(new MessageResponse("Refresh token is not in database!"))); // wrap error
    }


    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // find current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("No user is authenticated"));
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        refreshTokenService.deleteByUserId(userDetails.getId());
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
