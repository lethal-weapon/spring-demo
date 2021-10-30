package com.example.springdemo.data;

import com.example.springdemo.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository
         extends PagingAndSortingRepository<Employee, Long> {

  List<Employee> findAllByGenderIgnoreCase(String gender);

}
