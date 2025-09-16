package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Models.Department;

import java.util.List;

public interface DepartmentService
{
    Department create(Department department);
    Department getById(Long id);
    List<Department> getAll();
    Department update(Long id,Department department);
    void delete(Long id);
}
