package com.example.springdemo.data;

import com.example.springdemo.domain.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository
         extends PagingAndSortingRepository<Company, Long> {

}
