package com.service.demo.dto.response;

import com.service.demo.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChecklistResponseDTO {
  private String id;
  private String title;
  private String cardId;
  private List<Task> tasks;
}
