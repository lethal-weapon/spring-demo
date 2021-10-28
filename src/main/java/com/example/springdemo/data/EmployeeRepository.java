package com.example.springdemo.data;

import com.example.springdemo.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository
         extends PagingAndSortingRepository<Employee, Long> {

  Iterable<Employee> findAllByGenderIgnoreCase(String gender);

}
