package com.service.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardRequestDTO {
  private String id;
  private String title;
  private String description;
  private String catalogId;
  private List<String> attachments;
}
