package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Exceptions.ResourceNotFoundException;
import com.example.EmployeeCrud.Models.Project;
import com.example.EmployeeCrud.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private final ProjectRepository projectRepo;

    public ProjectServiceImpl(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
    }

    @Override
    public Project create(Project project) {
        return projectRepo.save(project);
    }

    @Override
    public Project getById(Long id) {
        return projectRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Project Not found with id "+id));
    }

    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }
}
