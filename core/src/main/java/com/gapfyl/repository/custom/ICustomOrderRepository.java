package com.gapfyl.repository.custom;

import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.Map;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

public interface ICustomOrderRepository {

    UpdateResult updateOrderStatus(String orderId, OrderStatus status, UserEntity userEntity);
}
