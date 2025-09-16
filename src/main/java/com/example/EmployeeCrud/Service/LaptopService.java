package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Models.Laptop;

import java.util.List;

public interface LaptopService {
    Laptop create(Laptop laptop);
    Laptop getById(Long id);
    List<Laptop> getAll();
}
