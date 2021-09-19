package com.gapfyl.services.checkouts.sales;

import com.gapfyl.dto.checkouts.sales.SalesRequest;
import com.gapfyl.enums.purchase.sales.SaleStatus;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.models.checkouts.orders.OrderItemEntity;
import com.gapfyl.models.checkouts.payments.PaymentEntity;
import com.gapfyl.models.checkouts.sales.SalesEntity;
import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.SalesRepository;
import com.gapfyl.services.checkouts.orders.OrderService;
import com.gapfyl.services.checkouts.payments.PaymentService;
import com.gapfyl.services.courses.UserCourseService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@Slf4j
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserCourseService userCourseService;

    void processOrderItem(OrderItemEntity orderItem, String orderId, String paymentId, UserEntity userEntity) {
        ProductEntity product = orderItem.getProduct();
        switch (product.getType()) {
            case course:
                userCourseService.addUserCourse(orderId, paymentId, product.getProductId(), userEntity);
        }
    }

    void processSalesItems(SalesEntity salesEntity, UserEntity userEntity) throws NotFoundException {
        OrderEntity orderEntity = orderService.fetchOrderEntity(salesEntity.getOrderId());
        if (orderEntity == null) {
            log.error("order {} not found", salesEntity.getOrderId());
            throw new NotFoundException("order.not_found", new Object[] { salesEntity.getOrderId() });
        }

        PaymentEntity paymentEntity = paymentService.fetchPaymentById(salesEntity.getPaymentId());
        if (paymentEntity == null) {
            log.error("order {} not found", salesEntity.getOrderId());
            throw new NotFoundException("payment.not_found", new Object[] { salesEntity.getOrderId() });
        }

        if (!Common.isNullOrEmpty(orderEntity.getItems())) {
            List<OrderItemEntity> orderItems = orderEntity.getItems();
            orderItems.forEach(item -> {
                processOrderItem(item, orderEntity.getOrderId(), paymentEntity.getPaymentId(), userEntity);
            });
        }
    }

    @Override
    public void addSales(SalesRequest salesRequest, UserEntity userEntity) throws NotFoundException {
        log.info("adding new sales item");
        SalesEntity salesEntity = new SalesEntity();
        salesEntity.setOrderId(salesRequest.getOrderId());
        salesEntity.setPaymentId(salesRequest.getPaymentId());
        salesEntity.setAccountId(userEntity.getAccount().getId());
        salesEntity.setUserId(userEntity.getId());
        salesEntity.setStatus(SaleStatus.success);
        salesEntity.setCreatedBy(userEntity);
        salesEntity.setModifiedBy(userEntity);
        salesEntity.setCreatedDate(Common.getCurrentUTCDate());
        salesEntity.setModifiedDate(Common.getCurrentUTCDate());
        salesEntity = salesRepository.save(salesEntity);
        processSalesItems(salesEntity, userEntity);
    }
}
