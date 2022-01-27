package com.service.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestDTO {
  private String title;
  private String description;
  private String catalogId;
}
