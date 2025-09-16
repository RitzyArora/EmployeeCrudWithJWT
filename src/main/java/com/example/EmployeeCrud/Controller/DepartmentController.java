package com.example.EmployeeCrud.Controller;

import com.example.EmployeeCrud.Models.Department;
import com.example.EmployeeCrud.Models.Employee;
import com.example.EmployeeCrud.Service.DepartmentService;
import com.example.EmployeeCrud.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){

        if(department!=null) {
            departmentService.create(department);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll(){
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(departmentService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id,@RequestBody Department department) throws Exception {
        return ResponseEntity.ok(departmentService.update(id,department));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
