package com.service.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CardResponseDTO {
  private String id;
  private String title;
  private String catalogId;
  private String description;
  private List<String> members;
  private String attachment;
}
