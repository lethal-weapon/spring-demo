package com.example.springdemo.controller;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public List<Employee> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable long id) {
    return service.findById(id);
  }

  @GetMapping(params = "gender")
  public List<Employee> getByGender(@RequestParam String gender) {
    return service.findByGender(gender);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Employee> getByPage(@RequestParam long page,
                                  @RequestParam long pageSize) {
    return service.findByPaging(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee unsaved) {
    return service.addNewEmployee(unsaved);
  }

  @PutMapping("/{id}")
  public Employee updateEmployee(@PathVariable long id,
                                 @RequestBody Employee updated) {

    if (updated.getId() == null) {
      throw new IllegalStateException("Given employee's ID doesn't exist.");
    }
    if (!updated.getId().equals(id)) {
      throw new IllegalStateException("Given employee's ID doesn't match the one in the path.");
    }
    if (service.findById(id) == null) {
      throw new IllegalStateException("Given employee doesn't exist.");
    }

    if (service.updateEmployee(updated)) {
      return updated;
    }
    throw new IllegalStateException("Given employee cannot be updated.");
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable long id) {
    service.deleteById(id);
  }
}
