package com.example.springdemo.controller;

import com.example.springdemo.dto.CompanyRequest;
import com.example.springdemo.dto.CompanyResponse;
import com.example.springdemo.dto.EmployeeResponse;
import com.example.springdemo.mapper.CompanyMapper;
import com.example.springdemo.mapper.EmployeeMapper;
import com.example.springdemo.service.CompanyService;
import com.example.springdemo.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private final CompanyService service;
  private final CompanyMapper mapper;
  private final EmployeeMapper employeeMapper;

  public CompanyController(CompanyService service,
                           CompanyMapper mapper,
                           EmployeeMapper employeeMapper) {
    this.service = service;
    this.mapper = mapper;
    this.employeeMapper = employeeMapper;
  }

  @GetMapping
  public List<CompanyResponse> getAll() {
    return mapper.fromEntity(
      service.findAll()
    );
  }

  @GetMapping("/{id}")
  public CompanyResponse getById(@PathVariable long id) {
    return mapper.fromEntity(
      service.findById(id)
    );
  }

  @GetMapping("/{id}/employees")
  public List<EmployeeResponse> getAllEmployeesByCompanyId(@PathVariable long id) {
    return employeeMapper.fromEntity(
      service.findAllEmployeesByCompanyId(id)
    );
  }

  @GetMapping(params = {"page", "pageSize"})
  public Page<CompanyResponse> getByPage(@RequestParam int page,
                                         @RequestParam int pageSize) {
    return mapper.fromEntity(
      service.findByPaging(page, pageSize)
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompanyResponse createCompany(@RequestBody CompanyRequest request) {
    Company unsaved = mapper.toEntity(request);
    Company saved = service.addNewCompany(unsaved);
    return mapper.fromEntity(saved);
  }

  @PutMapping("/{id}")
  public CompanyResponse updateCompany(@PathVariable long id,
                                       @RequestBody CompanyRequest request) {
    Company updated = mapper.toEntity(request);
    Company saved = service.updateCompany(id, updated);
    return mapper.fromEntity(saved);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable long id) {
    service.deleteById(id);
  }
}
