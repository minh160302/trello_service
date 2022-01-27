package com.service.demo.repository;

import com.service.demo.model.Catalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogRepository extends MongoRepository<Catalog, String> {
}
