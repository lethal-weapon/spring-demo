package com.example.springdemo.mapper;

import com.example.springdemo.domain.Employee;
import com.example.springdemo.dto.EmployeeRequest;
import com.example.springdemo.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class EmployeeMapper {

  public Employee toEntity(EmployeeRequest request) {
    Employee entity = new Employee();
    copyProperties(request, entity);
    return entity;
  }

  public EmployeeResponse fromEntity(Employee entity) {
    EmployeeResponse response = new EmployeeResponse();
    copyProperties(entity, response);
    return response;
  }

  public List<EmployeeResponse> fromEntity(List<Employee> entities) {
    return entities
      .stream()
      .map(this::fromEntity)
      .collect(Collectors.toList());
  }

  public Page<EmployeeResponse> fromEntity(Page<Employee> entities) {
    return entities.map(this::fromEntity);
  }
}
