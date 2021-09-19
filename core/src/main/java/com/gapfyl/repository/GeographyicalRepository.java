package com.gapfyl.repository;

import com.gapfyl.models.geodata.GeodataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GeographyicalRepository extends MongoRepository<GeodataEntity , String> {
}
