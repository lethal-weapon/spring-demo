package com.example.springdemo.domain;

import java.util.ArrayList;
import java.util.List;

public class Company {

  private Long id;
  private String name;
  private List<Employee> employees;

  public Company(Long id, String name) {
    this.id = id;
    this.name = name;
    this.employees = new ArrayList<>();
  }

  public void addEmployees(List<Employee> employees) {
    this.employees.addAll(employees);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }
}
