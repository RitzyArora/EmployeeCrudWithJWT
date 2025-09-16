package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Models.Employee;

import java.util.List;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee getById(Long id) throws Exception;
    List<Employee> getAll();
    Employee update(Long id,Employee employee) throws Exception;
    void delete(Long id) throws Exception;
}
