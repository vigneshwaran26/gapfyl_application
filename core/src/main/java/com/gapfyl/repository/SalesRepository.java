package com.gapfyl.repository;

import com.gapfyl.models.checkouts.sales.SalesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

public interface SalesRepository extends MongoRepository<SalesEntity, String> {
}
