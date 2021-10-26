package com.example.springdemo.controller;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private EmployeeRepository repo;

  public EmployeeController(EmployeeRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Employee> getAll() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable long id) {
    return repo.findById(id);
  }

  @GetMapping(params = "gender")
  public List<Employee> getByGender(@RequestParam String gender) {
    return repo.findByGender(gender);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Employee> getByPage(@RequestParam long page,
                                  @RequestParam long pageSize) {
    return repo.findByPaging(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee unsaved) {
    return repo.addNewEmployee(unsaved);
  }

}
