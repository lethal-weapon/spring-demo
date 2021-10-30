package com.example.springdemo.mapper;

import com.example.springdemo.domain.Company;
import com.example.springdemo.dto.CompanyRequest;
import com.example.springdemo.dto.CompanyResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CompanyMapper {

  private final EmployeeMapper employeeMapper;

  public CompanyMapper(EmployeeMapper employeeMapper) {
    this.employeeMapper = employeeMapper;
  }

  public Company toEntity(CompanyRequest request) {
    Company entity = new Company();
    copyProperties(request, entity);
    return entity;
  }

  public CompanyResponse fromEntity(Company entity) {
    CompanyResponse response = new CompanyResponse();

    copyProperties(entity, response);
    response.setEmployees(
      employeeMapper.fromEntity(
        entity.getEmployees()
      )
    );

    return response;
  }

  public List<CompanyResponse> fromEntity(List<Company> entities) {
    return entities
      .stream()
      .map(this::fromEntity)
      .collect(Collectors.toList());
  }

  public Page<CompanyResponse> fromEntity(Page<Company> entities) {
    return entities.map(this::fromEntity);
  }
}
