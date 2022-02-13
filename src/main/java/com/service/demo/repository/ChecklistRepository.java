package com.service.demo.repository;

import com.service.demo.model.Checklist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends MongoRepository<Checklist, String> {
}
