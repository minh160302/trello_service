package com.service.demo.dto.response.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ResponseClientDTO<T> {
  @JsonProperty("data")
  T data;

  @JsonProperty("status")
  Integer status;
}
