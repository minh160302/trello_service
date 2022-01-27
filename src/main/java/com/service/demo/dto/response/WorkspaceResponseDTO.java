package com.service.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class WorkspaceResponseDTO {
  private String id;

  private String email;

  private List<BoardResponseDTO> boards;

//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Instant createdDate;
}
