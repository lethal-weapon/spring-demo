package com.example.springdemo.controller;

import com.example.springdemo.service.CompanyService;
import com.example.springdemo.domain.Company;
import com.example.springdemo.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService service;

  public CompanyController(CompanyService service) {
    this.service = service;
  }

  @GetMapping
  public Iterable<Company> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public Company getById(@PathVariable long id) {
    return service.findById(id);
  }

  @GetMapping("/{id}/employees")
  public Iterable<Employee> getAllEmployeesByCompanyId(@PathVariable long id) {
    return service.findAllEmployeesByCompanyId(id);
  }

  @GetMapping(params = {"page", "pageSize"})
  public Iterable<Company> getByPage(@RequestParam int page,
                                     @RequestParam int pageSize) {
    return service.findByPaging(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Company createCompany(@RequestBody Company unsaved) {
    return service.addNewCompany(unsaved);
  }

  @PutMapping("/{id}")
  public Company updateCompany(@PathVariable long id,
                               @RequestBody Company updated) {
    return service.updateCompany(id, updated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable long id) {
    service.deleteById(id);
  }
}
