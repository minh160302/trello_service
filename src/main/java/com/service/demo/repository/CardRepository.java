package com.service.demo.repository;

import com.service.demo.model.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {
//  void deleteById(@NotNull String id);
}
