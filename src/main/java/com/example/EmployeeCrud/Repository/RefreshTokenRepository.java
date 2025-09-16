package com.example.EmployeeCrud.Repository;

import com.example.EmployeeCrud.Models.RefreshToken;
import com.example.EmployeeCrud.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
