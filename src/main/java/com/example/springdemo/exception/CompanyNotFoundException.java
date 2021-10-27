package com.example.springdemo.exception;

public class CompanyNotFoundException
     extends RuntimeException {

  public CompanyNotFoundException() {
    super("The company doesn't exist");
  }
}
