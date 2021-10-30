package com.example.springdemo.controller;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.dto.EmployeeRequest;
import com.example.springdemo.dto.EmployeeResponse;
import com.example.springdemo.mapper.EmployeeMapper;
import com.example.springdemo.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    return mapper.fromEntity(
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
    return mapper.fromEntity(
      service.findByGender(gender)
    );
  }

  @GetMapping(params = {"page", "pageSize"})
  public Page<EmployeeResponse> getByPage(@RequestParam int page,
                                          @RequestParam int pageSize) {
    return mapper.fromEntity(
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
    Employee saved = service.updateEmployee(id, updated);
    return mapper.fromEntity(saved);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable long id) {
    service.deleteById(id);
  }
}
