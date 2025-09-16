package com.example.EmployeeCrud.Repository;

import com.example.EmployeeCrud.Models.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
}
