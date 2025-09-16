package com.example.EmployeeCrud.Service;


import com.example.EmployeeCrud.Models.Project;

import java.util.List;

public interface ProjectService {

        Project create(Project project);
        Project getById(Long id);
        List<Project> getAll();


}
