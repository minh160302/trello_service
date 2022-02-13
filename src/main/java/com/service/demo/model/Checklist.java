package com.service.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("checklist")
public class Checklist {
  @Id
  private String id;
  private String title;
  private String cardId;
  private List<Task> tasks;
  private Double progress;
}
