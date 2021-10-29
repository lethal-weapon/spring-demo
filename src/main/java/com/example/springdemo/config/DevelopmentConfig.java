package com.example.springdemo.config;

import com.example.springdemo.data.CompanyRepository;
import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Company;
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
  public CommandLineRunner loadData(EmployeeRepository employeeRepo,
                                    CompanyRepository companyRepo) {
    return args -> {
      companyRepo.deleteAll();
      employeeRepo.deleteAll();

      Employee employeeA = new Employee("Alex", 33, "Male", 21500.00d);
      Employee employeeB = new Employee("James", 27, "Male", 18234.45d);
      Employee employeeC = new Employee("Emma", 29, "Female", 45123.73d);
      Employee employeeD = new Employee("Isabella", 24, "Female", 19533.45d);

      Company companyA = new Company("Apple");
      Company companyB = new Company("Google");
      Company companyC = new Company("IBM");

      companyA.addEmployee(employeeA);
      companyA.addEmployee(employeeB);
      companyB.addEmployee(employeeC);
      companyB.addEmployee(employeeD);
      companyC.addEmployee(employeeA);

      employeeRepo.saveAll(Arrays.asList(
        employeeA,
        employeeB,
        employeeC,
        employeeD
      ));
      companyRepo.saveAll(Arrays.asList(
        companyA,
        companyB,
        companyC
      ));
    };
  }
}
