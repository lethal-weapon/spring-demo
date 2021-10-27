package com.example.springdemo.advice;

import com.example.springdemo.exception.CompanyNotFoundException;
import com.example.springdemo.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler({
    EmployeeNotFoundException.class,
    CompanyNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse notFoundExceptionHandler(Exception exception) {
    return new ErrorResponse(404, exception.getMessage());
  }
}
