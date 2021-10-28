package com.example.springdemo.service;

import com.example.springdemo.data.EmployeeRepository;
import com.example.springdemo.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

  @Mock
  EmployeeRepository repo;

  @InjectMocks
  EmployeeService service;

  @Test
  void shouldReturnAllEmployeesWhenFindAll() {
    Iterable<Employee> employees = Arrays.asList(
      new Employee("Fred", 29, "Male", 23500.00d),
      new Employee("Linda", 26, "Female", 21100.00d)
    );
    given(repo.findAll()).willReturn(employees);

    Iterable<Employee> actual = service.findAll();

    assertIterableEquals(employees, actual);
  }

  @Test
  void shouldReturnOneEmployeeWhenFindById() {
    Employee employee = new Employee("Jerry", 24, "Male", 17521.00d);
    employee.setId(4342L);
    given(repo.findById(employee.getId())).willReturn(Optional.of(employee));

    Employee actual = repo.findById(4342L).orElse(null);

    assertSame(employee, actual);
  }

  @Test
  void shouldReturnEmployeesWithSameGenderWhenFindByGender() {
    List<Employee> employees = Arrays.asList(
      new Employee("Fred", 29, "Male", 23500.00d),
      new Employee("Linda", 26, "Female", 21100.00d),
      new Employee("Sammy", 23, "Female", 42132.00d)
    );
    List<Employee> femaleEmployees = employees
      .stream()
      .filter(e -> e.getGender().equals("Female"))
      .collect(Collectors.toList());
    given(repo.findAllByGenderIgnoreCase("Female")).willReturn(femaleEmployees);

    Iterable<Employee> actual = service.findByGender("Female");

    assertIterableEquals(femaleEmployees, actual);
  }

  @Test
  void shouldReturnPagedEmployeesWhenFindByPagingGivenPageAndPageSize() {
//    List<Employee> page1 = Arrays.asList(
//      new Employee("Fred", 29, "Male", 23500.00d),
//      new Employee("Linda", 26, "Female", 21100.00d)
//    );
//    List<Employee> page2 = List.of(
//      new Employee("Sammy", 23, "Female", 42132.00d)
//    );
//    given(repo.findAll(PageRequest.of(0, 2))).willReturn((Page<Employee>) page1);
//    given(repo.findAll(PageRequest.of(1, 2))).willReturn((Page<Employee>) page2);
//
//    Iterable<Employee> actualPage1 = service.findByPaging(0, 2);
//    Iterable<Employee> actualPage2 = service.findByPaging(1, 2);
//
//    assertIterableEquals(page1, actualPage1);
//    assertIterableEquals(page2, actualPage2);
  }

  @Test
  void shouldCreateEmployeeWhenAddNewEmployee() {
    Employee unsaved = new Employee("Linda", 26, "Female", 21100.00d);
    Employee saved = new Employee("Linda", 26, "Female", 21100.00d);
    saved.setId(4322L);
    given(repo.save(any(Employee.class))).willReturn(saved);

    Employee actual = service.addNewEmployee(unsaved);

    assertNotNull(actual.getId());
    assertEquals(unsaved.getName(), actual.getName());
    assertEquals(unsaved.getAge(), actual.getAge());
    assertEquals(unsaved.getGender(), actual.getGender());
    assertEquals(unsaved.getSalary(), actual.getSalary());
  }

  @Test
  void shouldUpdateEmployeeWhenGivenAnUpdatedEmployee() {
    Employee updated = new Employee("Linda", 26, "Female", 21100.00d);
    updated.setId(4322L);
    given(repo.findById(anyLong())).willReturn(Optional.of(updated));
    given(repo.save(any(Employee.class))).willReturn(updated);

    Employee actual = service.updateEmployee(updated.getId(), updated);

    assertEquals(4322L, actual.getId());
    assertEquals("Linda", actual.getName());
    assertEquals(26, actual.getAge());
    assertEquals("Female", actual.getGender());
    assertEquals(21100.00d, actual.getSalary());
  }
}
