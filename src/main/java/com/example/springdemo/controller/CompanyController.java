package com.example.springdemo.controller;

import com.example.springdemo.dto.CompanyRequest;
import com.example.springdemo.dto.CompanyResponse;
import com.example.springdemo.mapper.CompanyMapper;
import com.example.springdemo.service.CompanyService;
import com.example.springdemo.domain.Company;
import com.example.springdemo.domain.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService service;
  private final CompanyMapper mapper;

  public CompanyController(CompanyService service,
                           CompanyMapper mapper) {
    this.service = service;
    this.mapper = mapper;
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
  public CompanyResponse createCompany(@RequestBody CompanyRequest request) {
    Company unsaved = mapper.toEntity(request);
    Company saved = service.addNewCompany(unsaved);
    return mapper.fromEntity(saved);
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
