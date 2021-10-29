package com.example.springdemo.mapper;

import com.example.springdemo.domain.Company;
import com.example.springdemo.dto.CompanyRequest;
import com.example.springdemo.dto.CompanyResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompanyMapper {

  private final EmployeeMapper employeeMapper;

  public CompanyMapper(EmployeeMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  public Company toEntity(CompanyRequest request) {
    Company company = new Company();
    copyProperties(request, company);
    return company;
  }

  public CompanyResponse fromEntity(Company entity) {
    CompanyResponse response = new CompanyResponse();

    response.setId(entity.getId());
    response.setName(entity.getName());
    response.setEmployees(entity
      .getEmployees()
      .stream()
      .map(employeeMapper::fromEntity)
      .collect(Collectors.toList()));

    return response;
  }
}
