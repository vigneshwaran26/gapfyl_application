package com.gapfyl.repository;

import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.repository.custom.ICustomOrderRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

public interface OrderRepository extends MongoRepository<OrderEntity, String>, ICustomOrderRepository {
}
