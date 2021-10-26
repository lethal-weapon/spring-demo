package com.example.springdemo.controller;

import com.example.springdemo.data.CompanyRepository;
import com.example.springdemo.domain.Company;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

  private CompanyRepository repo;

  public CompanyController(CompanyRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Company> getAll() {
    return repo.findAll();
  }

  @GetMapping("/{id}")
  public Company getById(@PathVariable long id) {
    return repo.findById(id);
  }

  @GetMapping(params = {"page", "pageSize"})
  public List<Company> getByPage(@RequestParam long page,
                                 @RequestParam long pageSize) {
    return repo.findByPaging(page, pageSize);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Company createCompany(@RequestBody Company unsaved) {
    return repo.addNewCompany(unsaved);
  }

  @PutMapping("/{id}")
  public Company updateCompany(@PathVariable long id,
                               @RequestBody Company updated) {

    if (updated.getId() == null) {
      throw new IllegalStateException("Given company's ID doesn't exist.");
    }
    if (!updated.getId().equals(id)) {
      throw new IllegalStateException("Given company's ID doesn't match the one in the path.");
    }
    if (repo.findById(id) == null) {
      throw new IllegalStateException("Given company doesn't exist.");
    }

    if (repo.updateCompany(updated)) {
      return updated;
    }
    throw new IllegalStateException("Given company cannot be updated.");
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCompany(@PathVariable long id) {
    repo.deleteById(id);
  }
}
