package com.example.EmployeeCrud.Repository;

import com.example.EmployeeCrud.Models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
