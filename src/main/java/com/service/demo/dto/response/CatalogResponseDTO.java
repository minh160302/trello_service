package com.service.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class CatalogResponseDTO {
  private String id;
  private String title;

  private List<CardResponseDTO> cards;

  private String boardId;

  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Instant createdDate = Instant.now();

  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Instant updatedDate;
}
