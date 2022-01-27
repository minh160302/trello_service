package com.service.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidEntityIdException extends RuntimeException{
  public InvalidEntityIdException(String message) {
    super(message);
  }
}
