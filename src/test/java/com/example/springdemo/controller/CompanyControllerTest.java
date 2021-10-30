package com.example.springdemo.controller;

import com.example.springdemo.data.*;
import com.example.springdemo.domain.*;
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
class CompanyControllerTest {

  static final String BASE_URL = "/companies";

  @Autowired
  MockMvc mock;

  @Autowired
  CompanyRepository repo;

  @Autowired
  EmployeeRepository employeeRepo;

  @Autowired
  ObjectMapper jsonMapper;

  @BeforeEach
  void setUp() {
    repo.deleteAll();
    employeeRepo.deleteAll();
  }

  @AfterEach
  void tearDown() {
    repo.deleteAll();
    employeeRepo.deleteAll();
  }

  @Test
  void shouldReturnAllCompaniesWhenGetAllGivenMultipleCompanies() throws Exception {
    Company saved1 = repo.save(new Company("Postman"));
    Company saved2 = repo.save(new Company("Insomnia"));
    String url = String.format("%s", BASE_URL);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(saved1.getId()))
      .andExpect(jsonPath("$[0].name").value(saved1.getName()))
      .andExpect(jsonPath("$[0].employees").isEmpty())
      .andExpect(jsonPath("$[0].employees").isArray())
      .andExpect(jsonPath("$[1].id").value(saved2.getId()))
      .andExpect(jsonPath("$[1].name").value(saved2.getName()))
      .andExpect(jsonPath("$[1].employees").isEmpty())
      .andExpect(jsonPath("$[1].employees").isArray())
      .andExpect(jsonPath("$[2]").doesNotExist())
    ;
  }

  @Test
  void shouldReturnOneCompanyWhenGetOneGivenSingleCompany() throws Exception {
    Company saved = repo.save(new Company("Facebook"));
    String url = String.format("%s/%d", BASE_URL, saved.getId());

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.id").value(saved.getId()))
      .andExpect(jsonPath("$.name").value(saved.getName()))
      .andExpect(jsonPath("$.employees").isEmpty())
      .andExpect(jsonPath("$.employees").isArray())
    ;
  }

  @Test
  void shouldReturnAllEmployeesWithoutSalaryFromSameCompanyWhenGetEmployeeListByCompanyId() throws Exception {
    Company company = new Company("Twitter");
    Employee employeeA = new Employee("Earl", 43, "Male", 82333.00d);
    Employee employeeB = new Employee("Alexandra", 27, "Female", 39999.99d);
    company.addEmployee(employeeA);
    company.addEmployee(employeeB);
    Employee savedA = employeeRepo.save(employeeA);
    Employee savedB = employeeRepo.save(employeeB);
    Company saved = repo.save(company);
    String url = String.format("%s/%d/employees", BASE_URL, saved.getId());

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$").isArray())
      .andExpect(jsonPath("$[0].id").value(savedA.getId()))
      .andExpect(jsonPath("$[0].name").value(savedA.getName()))
      .andExpect(jsonPath("$[0].age").value(savedA.getAge()))
      .andExpect(jsonPath("$[0].gender").value(savedA.getGender()))
      .andExpect(jsonPath("$[0].salary").doesNotExist())
      .andExpect(jsonPath("$[1].id").value(savedB.getId()))
      .andExpect(jsonPath("$[1].name").value(savedB.getName()))
      .andExpect(jsonPath("$[1].age").value(savedB.getAge()))
      .andExpect(jsonPath("$[1].gender").value(savedB.getGender()))
      .andExpect(jsonPath("$[1].salary").doesNotExist())
      .andExpect(jsonPath("$[2]").doesNotExist())
    ;
  }

  @Test
  void shouldReturnPagedCompaniesWhenGetByPageGivenPageAndPageSize() throws Exception {
    Company saved1 = repo.save(new Company("Volkswagen"));
    Company saved2 = repo.save(new Company("Toyota"));
    Company saved3 = repo.save(new Company("Daimler"));
    String url = String.format("%s?page=%d&pageSize=%d", BASE_URL, 1, 2);

    ResultActions result = mock.perform(get(url));

    result
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").isNotEmpty())
      .andExpect(jsonPath("$.content").isNotEmpty())
      .andExpect(jsonPath("$.content").isArray())
      .andExpect(jsonPath("$.content[0].id").value(saved3.getId()))
      .andExpect(jsonPath("$.content[0].name").value(saved3.getName()))
      .andExpect(jsonPath("$.content[0].employees").isEmpty())
      .andExpect(jsonPath("$.content[0].employees").isArray())
      .andExpect(jsonPath("$.content[1]").doesNotExist())
    ;
  }

  @Test
  void shouldCreateCompanyWhenPostGivenACompany() throws Exception {
    Company unsaved = new Company("Ford");
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
      .andExpect(jsonPath("$.employees").isEmpty())
      .andExpect(jsonPath("$.employees").isArray())
    ;
  }

  @Test
  void shouldUpdateCompanyWhenPutGivenAnUpdatedCompany() throws Exception {
    Company unsaved = new Company("KFC");
    Company updated = repo.save(unsaved);
    updated.setName("McDonald's");
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
      .andExpect(jsonPath("$.employees").isEmpty())
      .andExpect(jsonPath("$.employees").isArray())
    ;
  }

  @Test
  void shouldDeleteCompanyWhenGivenACompanyId() throws Exception {
    Company saved = repo.save(new Company("LinkedIn"));
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
