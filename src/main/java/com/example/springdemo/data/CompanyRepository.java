package com.example.springdemo.data;

import com.example.springdemo.domain.Company;
import com.example.springdemo.domain.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

  private final List<Company> companies;

  public CompanyRepository() {
    companies = new ArrayList<>();
    companies.addAll(Arrays.asList(
      new Company(1L, "AeXL"),
      new Company(2L, "Apple"),
      new Company(3L, "BMW"),
      new Company(4L, "Hitachi")
    ));
    companies.get(0).addEmployees(Arrays.asList(
      new Employee(1L, "sean", 23, "male", 11111.00d),
      new Employee(2L, "wing", 22, "female", 22222.00d),
      new Employee(3L, "allen", 35, "male", 33333.00d),
      new Employee(4L, "steve", 65, "male", 99999.99d)
    ));
  }

  public List<Company> findAll() {
    return companies;
  }

  public Company findById(long id) {
    return companies
      .stream()
      .filter(e -> e.getId() == id)
      .findFirst()
      .orElse(null);
  }

  public List<Company> findByPaging(long page, long pageSize) {
    return companies
      .stream()
      .skip(page * pageSize)
      .limit(pageSize)
      .collect(Collectors.toList());
  }

  public Company addNewCompany(Company company) {
    company.setId(generateNewId());
    company
      .getEmployees()
      .forEach(e ->
        e.setId(((long) (Math.random() * Long.MAX_VALUE)) % 100000));
    companies.add(company);
    return company;
  }

  public boolean updateCompany(Company company) {
    if (deleteById(company.getId())) {
      companies.add(company);
      companies.sort((e1, e2) -> (int) (e1.getId() - e2.getId()));
      return true;
    }
    return false;
  }

  public boolean deleteById(long id) {
    Company company = findById(id);
    if (company != null) {
      companies.remove(company);
      return true;
    }
    return false;
  }

  private long generateNewId() {
    long max = companies
      .stream()
      .map(Company::getId)
      .max(Long::compareTo)
      .orElse(0L);

    return max + 1;
  }
}
