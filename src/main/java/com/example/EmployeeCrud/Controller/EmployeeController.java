package com.example.EmployeeCrud.Controller;

import com.example.EmployeeCrud.Models.Employee;
import com.example.EmployeeCrud.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        Employee newEmp = employeeService.create(employee);
        if (newEmp != null) {
            return new ResponseEntity<>(newEmp, HttpStatus.CREATED); // ✅ include body
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(employeeService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> update(@PathVariable Long id,@RequestBody Employee employee) throws Exception {
        return ResponseEntity.ok(employeeService.update(id,employee));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
