package com.example.springdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeResponse {

  private Long id;
  private String name;
  private Integer age;
  private String gender;

}
