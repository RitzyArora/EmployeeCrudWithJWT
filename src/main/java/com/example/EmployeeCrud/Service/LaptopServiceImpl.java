package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Exceptions.ResourceNotFoundException;
import com.example.EmployeeCrud.Models.Laptop;
import com.example.EmployeeCrud.Repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LaptopServiceImpl implements LaptopService{
    @Autowired
    private final LaptopRepository laptopRepo;

    public LaptopServiceImpl(LaptopRepository laptopRepo) {
        this.laptopRepo = laptopRepo;
    }

    @Override
    public Laptop create(Laptop laptop) {
        return laptopRepo.save(laptop);
    }

    @Override
    public Laptop getById(Long id) {
        return laptopRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Laptop Not found with id "+id));
    }

    @Override
    public List<Laptop> getAll() {
        return laptopRepo.findAll();
    }
}
