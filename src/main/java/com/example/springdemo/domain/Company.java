package com.example.springdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(targetEntity = Employee.class, fetch = FetchType.EAGER)
  private List<Employee> employees = new ArrayList<>();

  public Company(String name) {
    this.name = name;
  }

  public void addEmployee(Employee employee) {
    this.employees.add(employee);
  }
}
