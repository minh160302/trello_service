package com.service.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDTO {
  private String title;
  private String description;
  private String workspaceId;
}
