package com.service.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.File;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidResourceException extends RuntimeException{
  public InvalidResourceException(String message) {
    super(message);
  }
  public InvalidResourceException(String message, File file) {
    super(message);
    file.delete();
  }

}
