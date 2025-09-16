package com.example.EmployeeCrud.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String employeeName;
    @Column(nullable=false)
    private String employeeDesignation;
    @Column(nullable=false)
    private double employeeSalary;


    @ManyToOne
    @JoinColumn(name="department_id")
    @JsonBackReference
    private Department department;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="laptop_id")
    @JsonManagedReference
    private Laptop laptop;


    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "emp_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @JsonIgnoreProperties("employees")
    private Set<Project> projects=new HashSet<>();

    public void addProject(Project project) {
        this.projects.add(project);
        project.getEmployees().add(this);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public void setEmployeeSalary(double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }


}
