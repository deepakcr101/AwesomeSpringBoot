package com.deepak.payroll.controller;

import java.util.List;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deepak.payroll.exceptions.EmployeeNotFoundException;
import com.deepak.payroll.model.Employee;
import com.deepak.payroll.repository.EmployeeRepository;

@RestController
class EmployeeController {

  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }


  @GetMapping("/employees") // Maps HTTP GET requests for /employees to this method.
  List<Employee> all() {
      return repository.findAll(); // The repository gives you this method for free!
  }
  // end::get-aggregate-root[]

  @PostMapping("/employees")
  Employee newEmployee(@RequestBody Employee newEmployee) {
    return repository.save(newEmployee);
  }

  // Single item
  
  @GetMapping("/employees/{id}")
  EntityModel<Employee> one(@PathVariable Long id) { // @PathVariable binds the {id} from the URL to the 'id' parameter.

    Employee employee = repository.findById(id) //
        .orElseThrow(() -> new EmployeeNotFoundException(id));

    return EntityModel.of(employee, // The data
            linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(), // A link to this method itself ("self")
            linkTo(methodOn(EmployeeController.class).all()).withRel("employees")); // A link to the 'all' method
  }

  
  @PutMapping("/employees/{id}")
  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        return repository.save(newEmployee);
      });
  }

  @DeleteMapping("/employees/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
