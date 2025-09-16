package com.example.EmployeeCrud.Controller;

import com.example.EmployeeCrud.Models.Laptop;
import com.example.EmployeeCrud.Models.Project;
import com.example.EmployeeCrud.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project){

        if(project!=null) {
            projectService.create(project);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll(){
        return ResponseEntity.ok(projectService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(projectService.getById(id));
    }

}
