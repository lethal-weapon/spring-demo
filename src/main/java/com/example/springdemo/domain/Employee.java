package com.example.springdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Integer age;
  private String gender;
  private Double salary;

  public Employee(String name,
                  Integer age,
                  String gender,
                  Double salary) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.salary = salary;
  }
}
