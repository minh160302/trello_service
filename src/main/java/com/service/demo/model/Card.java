package com.service.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("card")
public class Card {
  @Id
  private String id;

  private String title;

  private String catalogId;

  private String description;

  private List<String> members;

  private List<String> attachments;
}
