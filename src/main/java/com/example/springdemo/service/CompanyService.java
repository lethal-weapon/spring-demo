package com.example.springdemo.service;

import com.example.springdemo.data.CompanyRepository;
import com.example.springdemo.domain.Company;
import com.example.springdemo.domain.Employee;
import com.example.springdemo.exception.CompanyNotFoundException;
import com.example.springdemo.exception.EntityIdNotExistedException;
import com.example.springdemo.exception.EntityIdNotMatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

  private final CompanyRepository repo;

  public CompanyService(CompanyRepository repo) {
    this.repo = repo;
  }

  public List<Company> findAll() {
    return (List<Company>) repo.findAll();
  }

  public Company findById(long id) {
    return repo
      .findById(id)
      .orElseThrow(CompanyNotFoundException::new);
  }

  public List<Employee> findAllEmployeesByCompanyId(long id) {
    return findById(id).getEmployees();
  }

  public Page<Company> findByPaging(int page,
                                    int pageSize) {
    PageRequest pageable = PageRequest
      .of(page, pageSize, Sort.by("id").ascending());

    return repo.findAll(pageable);
  }

  public Company addNewCompany(Company unsaved) {
    unsaved.setId(null);
    return repo.save(unsaved);
  }

  public Company updateCompany(long pathId,
                               Company updated) {
    if (updated.getId() == null) {
      throw new EntityIdNotExistedException();
    }
    if (!updated.getId().equals(pathId)) {
      throw new EntityIdNotMatchException();
    }

    Optional<Company> old = repo.findById(pathId);
    if (old.isEmpty()) {
      throw new CompanyNotFoundException();
    }

    updated.setEmployees(old.get().getEmployees());
    return repo.save(updated);
  }

  public void deleteById(long id) {
    try {
      repo.deleteById(id);
    } catch (EmptyResultDataAccessException ignored) {}
  }
}
