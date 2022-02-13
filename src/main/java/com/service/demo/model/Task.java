package com.service.demo.model;

import com.service.demo.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Task {
  private String title;
  private TaskStatus status;

  public Task(String title, TaskStatus status) {
    this.title = title;
    this.status = status;
  }
}
