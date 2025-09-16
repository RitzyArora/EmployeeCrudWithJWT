package com.example.EmployeeCrud.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Laptop
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String laptopBrand;

    @OneToOne(mappedBy = "laptop")
    @JsonBackReference
    private Employee employee;

    public Long getId() {
        return id;
    }

    public String getLaptopBrand() {
        return laptopBrand;
    }

    public void setLaptopBrand(String laptopBrand) {
        this.laptopBrand = laptopBrand;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
