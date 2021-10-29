package com.example.springdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanyResponse {

  private Long id;
  private String name;
  private List<EmployeeResponse> employees;

}
