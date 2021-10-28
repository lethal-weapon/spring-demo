package com.example.springdemo.advice;

import com.example.springdemo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler({
    EmployeeNotFoundException.class,
    CompanyNotFoundException.class
  })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse entityNotFoundExceptionHandler(Exception exception) {
    return new ErrorResponse(404, exception.getMessage());
  }

  @ExceptionHandler({
    EntityIdNotExistedException.class,
    EntityIdNotMatchException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse entityIdExceptionHandler(Exception exception) {
    return new ErrorResponse(400, exception.getMessage());
  }
}
