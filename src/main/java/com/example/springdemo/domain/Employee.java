package com.example.springdemo.domain;

public class Employee {

  private Long id;
  private String name;
  private Integer age;
  private String gender;
  private Double salary;

  public Employee(Long id,
                  String name,
                  Integer age,
                  String gender,
                  Double salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.salary = salary;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public Double getSalary() {
    return salary;
  }
}
