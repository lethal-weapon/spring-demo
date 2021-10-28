package com.example.springdemo.data;

import com.example.springdemo.domain.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository
         extends PagingAndSortingRepository<Company, Long> {

}
