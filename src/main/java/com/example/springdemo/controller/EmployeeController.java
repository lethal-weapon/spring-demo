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

  @PutMapping("/{id}")
  public Employee updateEmployee(@PathVariable long id,
                                 @RequestBody Employee updated) {

    if (updated.getId() == null) {
      throw new IllegalStateException("Given employee's ID doesn't exist.");
    }
    if (!updated.getId().equals(id)) {
      throw new IllegalStateException("Given employee's ID doesn't match the one in the path.");
    }
    if (repo.findById(id) == null) {
      throw new IllegalStateException("Given employee doesn't exist.");
    }

    if (repo.updateEmployee(updated)) {
      return updated;
    }
    throw new IllegalStateException("Given employee cannot be updated.");
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable long id) {
    repo.deleteById(id);
  }
}
