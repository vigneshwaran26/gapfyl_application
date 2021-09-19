package com.gapfyl.services.checkouts.orders;

import com.gapfyl.dto.checkouts.orders.OrderRequest;
import com.gapfyl.dto.checkouts.orders.OrderResponse;
import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.exceptions.purchases.orders.CreateOrderException;
import com.gapfyl.filter.purchases.orders.OrdersFilterCriteria;
import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.models.checkouts.orders.OrderItemEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.OrderRepository;
import com.gapfyl.services.products.ProductService;
import com.gapfyl.util.Common;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RazorpayClient razorpayClient;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    private Map<String, Object> createOrderInRazorPay(Double amount, String currency, Map notes)
            throws CreateOrderException {
        try {
            log.debug("creating order in razorpay");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", amount);
            jsonObject.put("currency", currency);

            Order order = razorpayClient.Orders.create(jsonObject);
            JSONObject orderJson = order.toJson();
            log.info("created order {}", orderJson);

            Map<String, Object> orderResponse = new HashMap();
            orderResponse.put("orderId", orderJson.get("id").toString());

            return orderResponse;
        } catch (RazorpayException e) {
            log.error("failed to create order {}", e.getMessage());
            throw new CreateOrderException(e.getMessage());
        }
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest, UserEntity userEntity)
            throws CreateOrderException {
        try {
            log.info("user {} [{}] creating a order", userEntity.getName(), userEntity.getId());
            Map<String, Object> razorpayOrder = createOrderInRazorPay(orderRequest.getAmount(),
                    orderRequest.getCurrency().name(), orderRequest.getNotes());

            if (Common.isNullOrEmpty(orderRequest.getProductIds())) {

            }

            List<OrderItemEntity> orderItemsList = new ArrayList<>();
            List<ProductEntity> productsList = productService.fetchProducts(orderRequest.getProductIds());
            log.info("found {} products from order request", productsList.size());

            for (ProductEntity product: productsList) {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setAmount(product.getPricing().getAmount());
                orderItem.setCurrency(product.getPricing().getCurrency());
                orderItem.setProduct(product);
                orderItemsList.add(orderItem);
            }

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId((String) razorpayOrder.get("orderId"));
            orderEntity.setAmount(orderRequest.getAmount());
            orderEntity.setCurrency(orderRequest.getCurrency());
            orderEntity.setItems(orderItemsList);
            orderEntity.setStatus(OrderStatus.ordered);
            orderEntity.setCreatedDate(Common.getCurrentUTCDate());
            orderEntity.setModifiedDate(Common.getCurrentUTCDate());
            orderEntity.setCreatedBy(userEntity);
            orderEntity.setModifiedBy(userEntity);
            orderRepository.save(orderEntity);
            log.info("order information added to table");

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(orderEntity.getId());
            orderResponse.setOrderId(orderEntity.getOrderId());
            orderResponse.setAmount(orderEntity.getAmount());
            orderResponse.setCurrency(orderEntity.getCurrency());
            return orderResponse;

        } catch (CreateOrderException e) {
            log.error("failed to create order {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public OrderEntity fetchOrderEntity(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderResponse> fetchOrders(OrdersFilterCriteria filterCriteria, UserEntity userEntity) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        return orderResponses;
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus status, UserEntity userEntity) {
        log.info("user {} [{}] updating order status as {}", userEntity.getName(), userEntity.getId(), status);
        orderRepository.updateOrderStatus(orderId, status, userEntity);
        log.info("user {} [{}] updated order status as {}", userEntity.getName(), userEntity.getId(), status);
    }
}
