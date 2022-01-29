package com.service.demo.dto.request.util;

import lombok.Getter;

@Getter
public class MoveCardRequestDTO {
  private String cardId;
  private String from;
  private String to;
  private Integer position;
}
