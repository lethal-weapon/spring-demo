package com.example.springdemo.controller;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  @Autowired
  MockMvc mock;

  @Autowired
  EmployeeRepository repo;

  @Autowired
  ObjectMapper jsonMapper;

  @BeforeEach
  void setUp() {
    repo.deleteAll();
  }

  @Test
  void shouldReturnAllEmployeesWhenGetAllGivenMultipleEmployees() throws Exception {
    Employee unsaved1 = new Employee("tom", 18, "Male", 17500.00d);
    Employee unsaved2 = new Employee("jen", 25, "Female", 29000.50d);
    Employee saved1 = repo.addNewEmployee(unsaved1);
    Employee saved2 = repo.addNewEmployee(unsaved2);
    String url = "/employees";

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved1.getId()))
      .andExpect(jsonPath("$[0].name").value(saved1.getName()))
      .andExpect(jsonPath("$[0].age").value(saved1.getAge()))
      .andExpect(jsonPath("$[0].gender").value(saved1.getGender()))
      .andExpect(jsonPath("$[0].salary").value(saved1.getSalary()))
      .andExpect(jsonPath("$[1].id").value(saved2.getId()))
      .andExpect(jsonPath("$[1].name").value(saved2.getName()))
      .andExpect(jsonPath("$[1].age").value(saved2.getAge()))
      .andExpect(jsonPath("$[1].gender").value(saved2.getGender()))
      .andExpect(jsonPath("$[1].salary").value(saved2.getSalary()))
    ;
  }

  @Test
  void shouldReturnOneEmployeeWhenGetOneGivenSingleEmployee() throws Exception {
    Employee unsaved = new Employee("jason", 32, "Male", 34500.00d);
    Employee saved = repo.addNewEmployee(unsaved);
    String url = String.format("/employees/%d", saved.getId());

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(saved.getId()))
      .andExpect(jsonPath("$.name").value(saved.getName()))
      .andExpect(jsonPath("$.age").value(saved.getAge()))
      .andExpect(jsonPath("$.gender").value(saved.getGender()))
      .andExpect(jsonPath("$.salary").value(saved.getSalary()))
    ;
  }

  @Test
  void shouldReturnEmployeesWithSameGenderWhenGetByGenderGivenGender() throws Exception {
    Employee unsaved1 = new Employee("tom", 18, "Male", 17500.00d);
    Employee unsaved2 = new Employee("jen", 25, "Female", 29000.00d);
    Employee unsaved3 = new Employee("sofia", 31, "Female", 45000.00d);
    Employee saved1 = repo.addNewEmployee(unsaved1);
    Employee saved2 = repo.addNewEmployee(unsaved2);
    Employee saved3 = repo.addNewEmployee(unsaved3);
    String url = "/employees?gender=Female";

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved2.getId()))
      .andExpect(jsonPath("$[0].name").value(saved2.getName()))
      .andExpect(jsonPath("$[0].age").value(saved2.getAge()))
      .andExpect(jsonPath("$[0].gender").value(saved2.getGender()))
      .andExpect(jsonPath("$[0].salary").value(saved2.getSalary()))
      .andExpect(jsonPath("$[1].id").value(saved3.getId()))
      .andExpect(jsonPath("$[1].name").value(saved3.getName()))
      .andExpect(jsonPath("$[1].age").value(saved3.getAge()))
      .andExpect(jsonPath("$[1].gender").value(saved3.getGender()))
      .andExpect(jsonPath("$[1].salary").value(saved3.getSalary()))
    ;
  }

  @Test
  void shouldReturnPagedEmployeesWhenGetByPageGivenPageAndPageSize() throws Exception {
    Employee unsaved1 = new Employee("tom", 18, "Male", 17500.00d);
    Employee unsaved2 = new Employee("jen", 25, "Female", 29000.00d);
    Employee unsaved3 = new Employee("sofia", 31, "Female", 45000.00d);
    Employee saved1 = repo.addNewEmployee(unsaved1);
    Employee saved2 = repo.addNewEmployee(unsaved2);
    Employee saved3 = repo.addNewEmployee(unsaved3);
    String url = "/employees?page=1&pageSize=2";

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved3.getId()))
      .andExpect(jsonPath("$[0].name").value(saved3.getName()))
      .andExpect(jsonPath("$[0].age").value(saved3.getAge()))
      .andExpect(jsonPath("$[0].gender").value(saved3.getGender()))
      .andExpect(jsonPath("$[0].salary").value(saved3.getSalary()))
    ;
  }

  @Test
  void shouldCreateEmployeeWhenPostGivenAnEmployee() throws Exception {
    Employee unsaved = new Employee("tom", 18, "Male", 17500.00d);
    String url = "/employees";

    ResultActions result = mock.perform(
      post(url)
        .contentType(APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(unsaved))
    );

    result
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(1))
      .andExpect(jsonPath("$.name").value(unsaved.getName()))
      .andExpect(jsonPath("$.age").value(unsaved.getAge()))
      .andExpect(jsonPath("$.gender").value(unsaved.getGender()))
      .andExpect(jsonPath("$.salary").value(unsaved.getSalary()))
    ;
  }

  @Test
  void shouldUpdateEmployeeWhenPutGivenAnEmployee() throws Exception {

  }

}
