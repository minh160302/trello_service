package com.service.demo.repository;

import com.service.demo.model.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
  Optional<Workspace> findByEmail(String email);
}
