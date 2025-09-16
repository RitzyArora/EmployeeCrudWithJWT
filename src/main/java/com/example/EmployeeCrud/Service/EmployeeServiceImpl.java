package com.example.EmployeeCrud.Service;

import com.example.EmployeeCrud.Exceptions.ResourceNotFoundException;
import com.example.EmployeeCrud.Models.Department;
import com.example.EmployeeCrud.Models.Employee;
import com.example.EmployeeCrud.Models.Laptop;
import com.example.EmployeeCrud.Models.Project;
import com.example.EmployeeCrud.Repository.DepartmentRepository;
import com.example.EmployeeCrud.Repository.EmployeeRepository;
import com.example.EmployeeCrud.Repository.LaptopRepository;
import com.example.EmployeeCrud.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private final EmployeeRepository empRepo;
    @Autowired
    private final LaptopRepository laptopRepo;
    @Autowired
    private final ProjectRepository projectRepo;
    @Autowired
    private final DepartmentRepository deptRepo;

    public EmployeeServiceImpl(EmployeeRepository empRepo,LaptopRepository laptopRepo,ProjectRepository projectRepo,DepartmentRepository deptRepo) {

        this.empRepo = empRepo;
        this.laptopRepo=laptopRepo;
        this.projectRepo=projectRepo;
        this.deptRepo=deptRepo;
    }

    @Override
    public Employee create(Employee employee) {
        Employee emp = new Employee();

        // Basic fields
        emp.setEmployeeName(employee.getEmployeeName());
        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
        emp.setEmployeeSalary(employee.getEmployeeSalary());

        // Department association
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            Department dept = deptRepo.findById(employee.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with id " + employee.getDepartment().getId()));
            emp.setDepartment(dept);
        }

        // Laptop association
        if (employee.getLaptop() != null && employee.getLaptop().getId() != null) {
            Laptop laptop = laptopRepo.findById(employee.getLaptop().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Laptop not found with id " + employee.getLaptop().getId()));
            emp.setLaptop(laptop);
            laptop.setEmployee(emp); // keep bidirectional consistency
        }

        // Projects association
        if (employee.getProjects() != null && !employee.getProjects().isEmpty()) {
            Set<Project> attachedProjects = new HashSet<>();
            for (Project p : employee.getProjects()) {
                Project managedProject = projectRepo.findById(p.getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Project not found with id " + p.getId()));
                // maintain both sides
                emp.addProject(managedProject);
                attachedProjects.add(managedProject);
            }
            emp.setProjects(attachedProjects);
        }

        return empRepo.save(emp);
    }





//    @Override
//    public Employee create(Employee employee) {
//        Employee emp = new Employee();
//        emp.setEmployeeName(employee.getEmployeeName());
//        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
//        emp.setEmployeeSalary(employee.getEmployeeSalary());
//        emp.setDepartment(employee.getDepartment());
//        if(employee.getLaptop()!=null && employee.getLaptop().getId()!=null)
//        {
//            Laptop laptop=laptopRepo.findById(employee.getLaptop().getId()).orElseThrow(
//                    ()->new ResourceNotFoundException("Laptop not found")
//            );
//            emp.setLaptop(laptop);
//        }
//
////        if (employee.getProjects() != null) {
////            for (Project p : employee.getProjects()) {
////                Project managedProject = projectRepo.findById(p.getId())
////                        .orElseThrow(() -> new ResourceNotFoundException(
////                                "Project not found with ID " + p.getId()));
////
////                // ✅ critical: use helper method so both sides are updated
////                emp.addProject(managedProject);
////            }
////        }
////        System.out.println("Projects count: " + emp.getProjects().size());
////
////        Employee saved = empRepo.save(emp);
////
////        return saved;
//        return empRepo.save(emp);
//    }

//    @Override
//    public Employee create(Employee employee) {
//
//        Employee emp = new Employee();
//        emp.setEmployeeName(employee.getEmployeeName());
//        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
//        emp.setEmployeeSalary(employee.getEmployeeSalary());
//        emp.setDepartment(employee.getDepartment());
//        emp.setLaptop(employee.getLaptop());
//
//        Set<Project> attachedProjects = new HashSet<>();
//        if (employee.getProjects() != null && !employee.getProjects().isEmpty()) {
//            for (Project p : employee.getProjects()) {
//                Project managedProject = projectRepo.findById(p.getId())
//                        .orElseThrow(() -> new ResourceNotFoundException(
//                                "Project not found with ID " + p.getId()));
//
//                // Maintain both sides of the relationship
//                emp.getProjects().add(managedProject);
//                managedProject.getEmployees().add(emp);
//
//                attachedProjects.add(managedProject);
//            }
//        }
//
//        emp.setProjects(attachedProjects);
//
//        return empRepo.save(emp);
//    }


//    @Override
//    public Employee create(Employee employee) {
//
//        Employee emp = new Employee();
//        emp.setEmployeeName(employee.getEmployeeName());
//        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
//        emp.setEmployeeSalary(employee.getEmployeeSalary());
//        emp.setDepartment(employee.getDepartment());
//        emp.setLaptop(employee.getLaptop());
//
//
//        Set<Project> attachedProjects = new HashSet<>();
//        if (employee.getProjects() != null && !employee.getProjects().isEmpty()) {
//            for (Project p : employee.getProjects()) {
//                Project managedProject = projectRepo.findById(p.getId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID " + p.getId()));
//                attachedProjects.add(managedProject);
//
//
//                if (managedProject.getEmployees() == null) {
//                    managedProject.setEmployees(new HashSet<>());
//                }
//                managedProject.getEmployees().add(emp);
//                attachedProjects.add(managedProject);
//            }
//        }
//        emp.setProjects(attachedProjects);
//
//            return empRepo.save(emp);
//
//
//    }

//    public Employee create(Employee employee) {
//        Employee emp=new Employee();
//        emp.setEmployeeName(employee.getEmployeeName());
//        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
//        emp.setEmployeeSalary(employee.getEmployeeSalary());
//        emp.setDepartment(employee.getDepartment());
////        emp.setLaptop(laptopRepo.findById(employee.getLaptop().getId()).orElseThrow(()->
////                new ResourceNotFoundException("Laptop not found")));
//
//        Set<Project> attachedProjects=new HashSet<>();
//        if(employee.getProjects()!=null)
//        {
//            for(Project p:employee.getProjects())
//            {
//                Project managed=projectRepo.findById(p.getId()).orElseThrow(
//                        ()->new ResourceNotFoundException("Project not found with "+p.getId())
//                );
//                attachedProjects.add(managed);
//                managed.getEmployees().add(emp);
//            }
//        }
//
//
//        emp.setLaptop(employee.getLaptop());
//        emp.setProjects(attachedProjects);
//
////        Employee saved = empRepo.save(emp);
////        saved.setProjects(new HashSet<>(saved.getProjects())); // force fetch
////        return saved;
//
//        return empRepo.save(emp);
//    }

    @Override
    public Employee getById(Long id){
      return empRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Not found with id "+id));
    }

    @Override
    public List<Employee> getAll() {
        return empRepo.findAll();
    }

    @Override
    public Employee update(Long id, Employee employee)  {
        Employee emp=getById(id);
        emp.setEmployeeName(employee.getEmployeeName());
        emp.setEmployeeDesignation(employee.getEmployeeDesignation());
        emp.setEmployeeSalary(employee.getEmployeeSalary());
        return empRepo.save(emp);
    }

    @Override
    public void delete(Long id)  {
     Employee emp=getById(id);
     empRepo.delete(emp);
    }
}
