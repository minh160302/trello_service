package com.service.demo.repository;

import com.service.demo.model.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, String> {
}
