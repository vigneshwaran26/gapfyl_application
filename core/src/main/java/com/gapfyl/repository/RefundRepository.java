package com.gapfyl.repository;

import com.gapfyl.models.checkouts.refunds.RefundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

public interface RefundRepository extends MongoRepository<RefundEntity, String> {
}
