package com.example.springdemo.config;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

  @Bean
  public CommandLineRunner loadData(EmployeeRepository employeeRepo) {
    return args -> {
      employeeRepo.deleteAll();

      employeeRepo.saveAll(Arrays.asList(
        new Employee("Alex", 33, "Male", 21500.00d),
        new Employee("James", 27, "Male", 18234.45d),
        new Employee("Emma", 29, "Female", 45123.73d),
        new Employee("Isabella", 24, "Female", 19533.45d)
      ));
    };
  }
}
