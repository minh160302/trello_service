package com.service.demo.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  @JsonProperty("id")
  String id;

  @JsonProperty("email")
  String email;

  @JsonProperty("username")
  String username;

  @JsonProperty("first_name")
  String firstName;

  @JsonProperty("last_name")
  String lastName;
}
