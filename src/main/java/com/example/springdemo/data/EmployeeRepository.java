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

  public Employee addNewEmployee(Employee employee) {
    employee.setId(generateNewId());
    employees.add(employee);
    return employee;
  }

  public boolean updateEmployee(Employee employee) {
    if (deleteById(employee.getId())) {
      employees.add(employee);
      employees.sort((e1, e2) -> (int) (e1.getId() - e2.getId()));
      return true;
    }
    return false;
  }

  public boolean deleteById(long id) {
    Employee employee = findById(id);
    if (employee != null) {
      employees.remove(employee);
      return true;
    }
    return false;
  }

  public void deleteAll() {
    employees.clear();
  }

  private long generateNewId() {
    long max = employees
      .stream()
      .map(Employee::getId)
      .max(Long::compareTo)
      .orElse(0L);

    return max + 1;
  }
}
