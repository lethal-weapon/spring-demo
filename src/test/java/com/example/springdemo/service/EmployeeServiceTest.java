//package com.example.springdemo.service;
//
//import com.example.springdemo.data.EmployeeRepository;
//import com.example.springdemo.domain.Employee;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.mockito.Mock;
//import org.mockito.InjectMocks;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.*;
//
//@ExtendWith(SpringExtension.class)
//class EmployeeServiceTest {
//
//  @Mock
//  EmployeeRepository repo;
//
//  @InjectMocks
//  EmployeeService service;
//
//  @Test
//  void shouldReturnAllEmployeesWhenFindAll() {
//    List<Employee> employees = Arrays.asList(
//      new Employee("Fred", 29, "Male", 23500.00d),
//      new Employee("Linda", 26, "Female", 21100.00d)
//    );
//    given(repo.findAll()).willReturn(employees);
//
//    List<Employee> actual = service.findAll();
//
//    assertIterableEquals(employees, actual);
//  }
//
//  @Test
//  void shouldReturnOneEmployeeWhenFindById() {
//    Employee employee = new Employee("Jerry", 24, "Male", 17521.00d);
//    employee.setId(4342L);
//    given(repo.findById(employee.getId())).willReturn(employee);
//
//    Employee actual = repo.findById(4342);
//
//    assertSame(employee, actual);
//  }
//
//  @Test
//  void shouldReturnEmployeesWithSameGenderWhenFindByGender() {
//    List<Employee> employees = Arrays.asList(
//      new Employee("Fred", 29, "Male", 23500.00d),
//      new Employee("Linda", 26, "Female", 21100.00d),
//      new Employee("Sammy", 23, "Female", 42132.00d)
//    );
//    List<Employee> femaleEmployees = employees
//      .stream()
//      .filter(e -> e.getGender().equals("Female"))
//      .collect(Collectors.toList());
//    given(repo.findByGender("Female")).willReturn(femaleEmployees);
//
//    List<Employee> actual = service.findByGender("Female");
//
//    assertIterableEquals(femaleEmployees, actual);
//  }
//
//  @Test
//  void shouldReturnPagedEmployeesWhenFindByPagingGivenPageAndPageSize() {
//    List<Employee> page1 = Arrays.asList(
//      new Employee("Fred", 29, "Male", 23500.00d),
//      new Employee("Linda", 26, "Female", 21100.00d)
//    );
//    List<Employee> page2 = List.of(
//      new Employee("Sammy", 23, "Female", 42132.00d)
//    );
//    given(repo.findByPaging(0, 2)).willReturn(page1);
//    given(repo.findByPaging(1, 2)).willReturn(page2);
//
//    List<Employee> actualPage1 = service.findByPaging(0, 2);
//    List<Employee> actualPage2 = service.findByPaging(1, 2);
//
//    assertIterableEquals(page1, actualPage1);
//    assertIterableEquals(page2, actualPage2);
//  }
//
//  @Test
//  void shouldCreateEmployeeWhenAddNewEmployee() {
//    Employee unsaved = new Employee("Linda", 26, "Female", 21100.00d);
//    Employee saved = new Employee("Linda", 26, "Female", 21100.00d);
//    saved.setId(4322L);
//    given(repo.addNewEmployee(any(Employee.class))).willReturn(saved);
//
//    Employee actual = service.addNewEmployee(unsaved);
//
//    assertNotNull(actual.getId());
//    assertEquals(unsaved.getName(), actual.getName());
//    assertEquals(unsaved.getAge(), actual.getAge());
//    assertEquals(unsaved.getGender(), actual.getGender());
//    assertEquals(unsaved.getSalary(), actual.getSalary());
//  }
//
//  @Test
//  void shouldUpdateEmployeeWhenGivenAnUpdatedEmployee() {
//    Employee updated = new Employee("Linda", 26, "Female", 21100.00d);
//    updated.setId(4322L);
//    given(repo.updateEmployee(any(Employee.class))).willReturn(true);
//
//    boolean actual = service.updateEmployee(updated);
//
//    assertTrue(actual);
//  }
//
//  @Test
//  void shouldDeleteEmployeeWhenDeleteByEmployeeId() {
//    given(repo.findById(anyLong())).willReturn(null);
//
//    Employee actual = repo.findById(4342L);
//
//    assertNull(actual);
//  }
//}
