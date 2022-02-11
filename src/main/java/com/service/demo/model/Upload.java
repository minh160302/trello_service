package com.service.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Setter
@Getter
@Document("uploads")
public class Upload {
  @Id
  private String id;

  private String name;
  private String path;
  private String type;
  private String key;

  @JsonProperty("created_date")
  private Instant createdDate = Instant.now();
}
