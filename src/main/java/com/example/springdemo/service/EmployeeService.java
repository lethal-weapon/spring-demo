package com.example.springdemo.service;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import com.example.springdemo.exception.EmployeeNotFoundException;
import com.example.springdemo.exception.EntityIdNotExistedException;
import com.example.springdemo.exception.EntityIdNotMatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

  private final EmployeeRepository repo;

  public EmployeeService(EmployeeRepository repo) {
    this.repo = repo;
  }

  public List<Employee> findAll() {
    return (List<Employee>) repo.findAll();
  }

  public Employee findById(long id) {
    return repo
      .findById(id)
      .orElseThrow(EmployeeNotFoundException::new);
  }

  public List<Employee> findByGender(String gender) {
    return repo.findAllByGenderIgnoreCase(gender);
  }

  public Page<Employee> findByPaging(int page,
                                     int pageSize) {
    PageRequest pageable = PageRequest
      .of(page, pageSize, Sort.by("id").ascending());

    return repo.findAll(pageable);
  }

  public Employee addNewEmployee(Employee unsaved) {
    unsaved.setId(null);
    return repo.save(unsaved);
  }

  public Employee updateEmployee(long pathId,
                                 Employee updated) {
    if (updated.getId() == null) {
      throw new EntityIdNotExistedException();
    }
    if (!updated.getId().equals(pathId)) {
      throw new EntityIdNotMatchException();
    }
    if (repo.findById(pathId).isEmpty()) {
      throw new EmployeeNotFoundException();
    }

    return repo.save(updated);
  }

  public void deleteById(long id) {
    repo.deleteById(id);
  }
}
