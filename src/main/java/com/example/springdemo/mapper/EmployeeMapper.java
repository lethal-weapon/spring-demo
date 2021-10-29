package com.example.springdemo.mapper;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.dto.EmployeeRequest;
import com.example.springdemo.dto.EmployeeResponse;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class EmployeeMapper {

  public Employee toEntity(EmployeeRequest request) {
    Employee employee = new Employee();
    copyProperties(request, employee);
    return employee;
  }

  public EmployeeResponse fromEntity(Employee entity) {
    EmployeeResponse response = new EmployeeResponse();
    copyProperties(entity, response);
    return response;
  }
}
