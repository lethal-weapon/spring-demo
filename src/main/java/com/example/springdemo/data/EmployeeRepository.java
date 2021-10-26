package com.example.springdemo.data;

import com.example.springdemo.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

  private final List<Employee> employees;

  public EmployeeRepository() {
    employees = new ArrayList<>();
    employees.addAll(Arrays.asList(
      new Employee(1L, "sean", 23, "male", 11111.00d),
      new Employee(2L, "wing", 22, "female", 22222.00d),
      new Employee(3L, "allen", 35, "male", 33333.00d),
      new Employee(4L, "steve", 65, "male", 99999.99d)
    ));
  }

  public List<Employee> findAll() {
    return employees;
  }

  public Employee findById(long id) {
    return employees
      .stream()
      .filter(e -> e.getId() == id)
      .findFirst()
      .orElse(null);
  }

  public List<Employee> findByGender(String gender) {
    return employees
      .stream()
      .filter(e -> e.getGender().equals(gender))
      .collect(Collectors.toList());
  }

  public List<Employee> findByPaging(long page, long pageSize) {
    return employees
      .stream()
      .skip(page * pageSize)
      .limit(pageSize)
      .collect(Collectors.toList());
  }
}
