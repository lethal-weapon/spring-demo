package com.example.springdemo.service;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

  private EmployeeRepository repo;

  public EmployeeService(EmployeeRepository repo) {
    this.repo = repo;
  }

  public List<Employee> findAll() {
    return repo.findAll();
  }

  public Employee findById(long id) {
    return repo.findById(id);
  }

  public List<Employee> findByGender(String gender) {
    return repo.findByGender(gender);
  }

  public List<Employee> findByPaging(long page, long pageSize) {
    return repo.findByPaging(page, pageSize);
  }

  public Employee addNewEmployee(Employee unsaved) {
    return repo.addNewEmployee(unsaved);
  }

  public boolean updateEmployee(Employee updated) {
    return repo.updateEmployee(updated);
  }

  public void deleteById(long id) {
    repo.deleteById(id);
  }
}
