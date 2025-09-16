package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Exceptions.ResourceNotFoundException;
import com.example.EmployeeCrud.Models.Department;

import com.example.EmployeeCrud.Repository.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private final DepartmentRepository deptRepo;

    public DepartmentServiceImpl(DepartmentRepository deptRepo) {
        this.deptRepo = deptRepo;
    }

    @Override
    public Department create(Department department) {

        return deptRepo.save(department);
    }

    @Override
    public Department getById(Long id){
        return deptRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found with id "+id));
    }

    @Override
    public List<Department> getAll() {
        return deptRepo.findAll();
    }

    @Override
    public Department update(Long id, Department department)  {
        Department dept=getById(id);
        dept.setDepartmentName(department.getDepartmentName());

        return deptRepo.save(dept);
    }

    @Override
    public void delete(Long id)  {
        Department emp=getById(id);
        deptRepo.delete(emp);
    }
    }