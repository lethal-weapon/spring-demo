package com.example.springdemo.controller;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

  static final String BASE_URL = "/employees";

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

  @AfterEach
  void tearDown() {
    repo.deleteAll();
  }

  @Test
  void shouldReturnAllEmployeesWithoutSalaryWhenGetAllGivenMultipleEmployees() throws Exception {
    Employee saved1 = repo.save(new Employee("Tom", 18, "Male", 17500.00d));
    Employee saved2 = repo.save(new Employee("Jen", 25, "Female", 29000.50d));
    String url = String.format("%s", BASE_URL);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved1.getId()))
      .andExpect(jsonPath("$[0].name").value(saved1.getName()))
      .andExpect(jsonPath("$[0].age").value(saved1.getAge()))
      .andExpect(jsonPath("$[0].gender").value(saved1.getGender()))
      .andExpect(jsonPath("$[0].salary").doesNotExist())
      .andExpect(jsonPath("$[1].id").value(saved2.getId()))
      .andExpect(jsonPath("$[1].name").value(saved2.getName()))
      .andExpect(jsonPath("$[1].age").value(saved2.getAge()))
      .andExpect(jsonPath("$[1].gender").value(saved2.getGender()))
      .andExpect(jsonPath("$[1].salary").doesNotExist())
      .andExpect(jsonPath("$[2]").doesNotExist())
    ;
  }

  @Test
  void shouldReturnOneEmployeeWithoutSalaryWhenGetOneGivenSingleEmployee() throws Exception {
    Employee saved = repo.save(new Employee("Jason", 32, "Male", 34500.00d));
    String url = String.format("%s/%d", BASE_URL, saved.getId());

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(saved.getId()))
      .andExpect(jsonPath("$.name").value(saved.getName()))
      .andExpect(jsonPath("$.age").value(saved.getAge()))
      .andExpect(jsonPath("$.gender").value(saved.getGender()))
      .andExpect(jsonPath("$.salary").doesNotExist())
    ;
  }

  @Test
  void shouldReturnEmployeesWithSameGenderWithoutSalaryWhenGetByGender() throws Exception {
    Employee saved1 = repo.save(new Employee("Tom", 18, "Male", 17500.00d));
    Employee saved2 = repo.save(new Employee("Jen", 25, "Female", 29000.00d));
    Employee saved3 = repo.save(new Employee("Sofia", 31, "Female", 45000.00d));
    String url = String.format("%s?gender=%s", BASE_URL, "Female");

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved2.getId()))
      .andExpect(jsonPath("$[0].name").value(saved2.getName()))
      .andExpect(jsonPath("$[0].age").value(saved2.getAge()))
      .andExpect(jsonPath("$[0].gender").value(saved2.getGender()))
      .andExpect(jsonPath("$[0].salary").doesNotExist())
      .andExpect(jsonPath("$[1].id").value(saved3.getId()))
      .andExpect(jsonPath("$[1].name").value(saved3.getName()))
      .andExpect(jsonPath("$[1].age").value(saved3.getAge()))
      .andExpect(jsonPath("$[1].gender").value(saved3.getGender()))
      .andExpect(jsonPath("$[1].salary").doesNotExist())
      .andExpect(jsonPath("$[2]").doesNotExist())
    ;
  }

  @Test
  void shouldReturnPagedEmployeesWhenGetByPageGivenPageAndPageSize() throws Exception {
    Employee saved1 = repo.save(new Employee("Tom", 18, "Male", 17500.00d));
    Employee saved2 = repo.save(new Employee("Jen", 25, "Female", 29000.00d));
    Employee saved3 = repo.save(new Employee("Sofia", 31, "Female", 45000.00d));
    String url = String.format("%s?page=%d&pageSize=%d", BASE_URL, 1, 2);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.content").isNotEmpty())
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content[0].id").value(saved3.getId()))
      .andExpect(jsonPath("$.content[0].name").value(saved3.getName()))
      .andExpect(jsonPath("$.content[0].age").value(saved3.getAge()))
      .andExpect(jsonPath("$.content[0].gender").value(saved3.getGender()))
      .andExpect(jsonPath("$.content[0].salary").doesNotExist())
      .andExpect(jsonPath("$.content[1]").doesNotExist())
    ;
  }

  @Test
  void shouldCreateEmployeeWhenPostGivenAnEmployee() throws Exception {
    Employee unsaved = new Employee("Tom", 18, "Male", 17500.00d);
    String url = String.format("%s", BASE_URL);
    assertEquals(0L, repo.count());

    ResultActions result = mock.perform(
      post(url)
        .contentType(APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(unsaved))
    );

    assertEquals(1L, repo.count());
    result
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").isNumber())
      .andExpect(jsonPath("$.name").value(unsaved.getName()))
      .andExpect(jsonPath("$.age").value(unsaved.getAge()))
      .andExpect(jsonPath("$.gender").value(unsaved.getGender()))
      .andExpect(jsonPath("$.salary").doesNotExist())
    ;
  }

  @Test
  void shouldUpdateEmployeeWhenPutGivenAnUpdatedEmployee() throws Exception {
    Employee unsaved = new Employee("Jason", 32, "Male", 34500.00d);
    Employee updated = repo.save(unsaved);
    updated.setName("Zoey");
    updated.setAge(33);
    updated.setGender("Female");
    updated.setSalary(36000.00d);
    String url = String.format("%s/%d", BASE_URL, updated.getId());

    ResultActions result = mock.perform(
      put(url)
        .contentType(APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(updated))
    );

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(updated.getId()))
      .andExpect(jsonPath("$.name").value(updated.getName()))
      .andExpect(jsonPath("$.age").value(updated.getAge()))
      .andExpect(jsonPath("$.gender").value(updated.getGender()))
      .andExpect(jsonPath("$.salary").doesNotExist())
    ;
  }

  @Test
  void shouldDeleteEmployeeWhenGivenAnEmployeeId() throws Exception {
    Employee saved = repo.save(new Employee("Jason", 32, "Male", 34500.00d));
    String url = String.format("%s/%d", BASE_URL, saved.getId());
    assertEquals(1L, repo.count());

    ResultActions result = mock.perform(delete(url));

    assertEquals(0L, repo.count());
    result
      .andExpect(status().isNoContent())
      .andExpect(jsonPath("$").doesNotExist())
    ;
  }
}
