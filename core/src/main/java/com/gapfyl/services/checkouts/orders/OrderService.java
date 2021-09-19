package com.gapfyl.services.checkouts.orders;

import com.gapfyl.dto.checkouts.orders.OrderRequest;
import com.gapfyl.dto.checkouts.orders.OrderResponse;
import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.exceptions.purchases.orders.CreateOrderException;
import com.gapfyl.exceptions.purchases.orders.OrderNotFoundException;
import com.gapfyl.exceptions.purchases.orders.OrderUpdateFailedException;
import com.gapfyl.filter.purchases.orders.OrdersFilterCriteria;
import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.models.users.UserEntity;
import com.razorpay.Order;

import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest, UserEntity userEntity) throws CreateOrderException;

    OrderEntity fetchOrderEntity(String orderId);

    List<OrderResponse> fetchOrders(OrdersFilterCriteria filterCriteria, UserEntity userEntity);

    void updateOrderStatus(String orderId, OrderStatus status, UserEntity userEntity);
}
