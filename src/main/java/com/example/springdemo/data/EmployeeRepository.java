package com.example.springdemo.data;

import com.example.springdemo.domain.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository
         extends PagingAndSortingRepository<Employee, Long> {

  List<Employee> findAllByGenderIgnoreCase(String gender);

}
