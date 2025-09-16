package com.example.EmployeeCrud.Controller;

import com.example.EmployeeCrud.Models.Department;
import com.example.EmployeeCrud.Models.Laptop;
import com.example.EmployeeCrud.Service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {
    @Autowired
    private final LaptopService laptopService;


    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @PostMapping
    public ResponseEntity<Laptop> createLaptop(@RequestBody Laptop laptop){

        if(laptop!=null) {
            laptopService.create(laptop);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Laptop>> getAll(){
        return ResponseEntity.ok(laptopService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptopById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(laptopService.getById(id));
    }

}
