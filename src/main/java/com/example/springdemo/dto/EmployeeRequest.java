package com.example.springdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeRequest {

  private String name;
  private Integer age;
  private String gender;
  private Double salary;

}
