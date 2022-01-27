package com.service.demo.dto.response.common;

import org.springframework.http.HttpStatus;

public class BaseResponseDTO<T> {
  private T data;
  private Integer status;

  public BaseResponseDTO(T data, HttpStatus status) {
    this.data = data;
    this.status = status.value();
  }

  public T getData() {
    return this.data;
  }

  public Integer getStatus() {
    return this.status;
  }
}
