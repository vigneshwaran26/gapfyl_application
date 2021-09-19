package com.gapfyl.repository.impl;

import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomOrderRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

public class OrderRepositoryImpl implements ICustomOrderRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UpdateResult updateOrderStatus(String orderId, OrderStatus status, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("order_id").is(orderId));

        Update update = new Update();
        update.set("status", status);
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, OrderEntity.class);
    }
}
