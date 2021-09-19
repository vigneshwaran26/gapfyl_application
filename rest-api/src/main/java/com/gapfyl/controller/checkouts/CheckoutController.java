package com.gapfyl.controller.checkouts;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.checkouts.orders.OrderRequest;
import com.gapfyl.dto.checkouts.payments.PaymentRequest;
import com.gapfyl.dto.checkouts.payments.VerifyPaymentSignature;
import com.gapfyl.dto.checkouts.refunds.RefundRequest;
import com.gapfyl.exceptions.purchases.orders.CreateOrderException;
import com.gapfyl.exceptions.purchases.orders.PaymentSignatureException;
import com.gapfyl.exceptions.purchases.payments.PaymentNotFoundException;
import com.gapfyl.exceptions.purchases.refunds.RefundException;
import com.gapfyl.filter.purchases.orders.OrdersFilterCriteria;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.checkouts.orders.OrderService;
import com.gapfyl.services.checkouts.payments.PaymentService;
import com.gapfyl.services.checkouts.refunds.RefundService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@RestController
@RequestMapping("/api/1.0/checkouts")
public class CheckoutController extends AbstractController {

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    RefundService refundService;

    @PostMapping("/orders/create")
    ResponseEntity createOrder(@RequestBody OrderRequest orderRequest) throws CreateOrderException {
        return ResponseEntity.ok().body(orderService.createOrder(orderRequest, getCurrentUser()));
    }

    @GetMapping("/orders/fetch")
    ResponseEntity fetchOrders(@RequestBody OrdersFilterCriteria filterCriteria) {
        return ResponseEntity.ok().body(orderService.fetchOrders(filterCriteria, getCurrentUser()));
    }

    @PostMapping("/payments/create")
    ResponseEntity createPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(paymentService.createPayment(paymentRequest, userEntity));
    }

    @PostMapping("/payments/verify-signature")
    ResponseEntity verifyPayment(@RequestBody @Valid VerifyPaymentSignature verifyPaymentSignature) throws PaymentSignatureException {
        UserEntity userEntity = getCurrentUser();
        boolean verified = paymentService.verifyPayment(verifyPaymentSignature, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", verified).build());
    }

    @PostMapping("/refunds/request")
    ResponseEntity requestRefund(@RequestBody RefundRequest refundRequest)
            throws PaymentNotFoundException, RefundException {
        return ResponseEntity.ok().body(refundService.requestRefund(refundRequest, getCurrentUser()));
    }
}
