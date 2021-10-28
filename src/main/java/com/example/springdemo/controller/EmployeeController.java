package com.example.springdemo.controller;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<Employee> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable long id) {
    return service.findById(id);
  }

  @GetMapping(params = "gender")
  public Iterable<Employee> getByGender(@RequestParam String gender) {
    return service.findByGender(gender);
  }

  @GetMapping(params = {"page", "pageSize"})
  public Iterable<Employee> getByPage(@RequestParam int page,
                                      @RequestParam int pageSize) {
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
    return service.updateEmployee(id, updated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable long id) {
    service.deleteById(id);
  }
}
