package com.example.springdemo.controller;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.dto.EmployeeRequest;
import com.example.springdemo.dto.EmployeeResponse;
import com.example.springdemo.mapper.EmployeeMapper;
import com.example.springdemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService service;
  private final EmployeeMapper mapper;

  public EmployeeController(EmployeeService service,
                            EmployeeMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping
  public List<EmployeeResponse> getAll() {
    return toResponse(
      service.findAll()
    );
  }

  @GetMapping("/{id}")
  public EmployeeResponse getById(@PathVariable long id) {
    return mapper.fromEntity(
      service.findById(id)
    );
  }

  @GetMapping(params = "gender")
  public List<EmployeeResponse> getByGender(@RequestParam String gender) {
    return toResponse(
      service.findByGender(gender)
    );
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<EmployeeResponse> getByPage(@RequestParam int page,
                                          @RequestParam int pageSize) {
    return toResponse(
      service.findByPaging(page, pageSize)
    );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeResponse createEmployee(@RequestBody EmployeeRequest request) {
    Employee unsaved = mapper.toEntity(request);
    Employee saved = service.addNewEmployee(unsaved);
    return mapper.fromEntity(saved);
  }

  @PutMapping("/{id}")
  public EmployeeResponse updateEmployee(@PathVariable long id,
                                         @RequestBody EmployeeRequest request) {
    Employee updated = mapper.toEntity(request);
    return mapper.fromEntity(
      service.updateEmployee(id, updated)
    );
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable long id) {
    service.deleteById(id);
  }

  private List<EmployeeResponse> toResponse(List<Employee> employees) {
    return employees
      .stream()
      .map(mapper::fromEntity)
      .collect(Collectors.toList());
  }
}
