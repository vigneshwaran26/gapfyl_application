package com.gapfyl.services.checkouts.refunds;

import com.gapfyl.dto.checkouts.refunds.RefundRequest;
import com.gapfyl.dto.checkouts.refunds.RefundResponse;
import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.enums.purchase.payments.PaymentStatus;
import com.gapfyl.enums.purchase.refunds.RefundSpeed;
import com.gapfyl.enums.purchase.refunds.RefundStatus;
import com.gapfyl.enums.purchase.refunds.RefundType;
import com.gapfyl.exceptions.purchases.payments.PaymentNotFoundException;
import com.gapfyl.exceptions.purchases.refunds.RefundException;
import com.gapfyl.models.checkouts.payments.PaymentEntity;
import com.gapfyl.models.checkouts.refunds.RefundEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.PaymentRepository;
import com.gapfyl.repository.RefundRepository;
import com.gapfyl.services.checkouts.orders.OrderService;
import com.gapfyl.services.checkouts.payments.PaymentService;
import com.gapfyl.services.courses.UserCourseService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Refund;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

@Slf4j
@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    RazorpayClient razorpayClient;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    RefundRepository refundRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserCourseService userCourseService;

    @Override
    public RefundResponse requestRefund(RefundRequest refundRequest, UserEntity userEntity) throws PaymentNotFoundException, RefundException {
        // FIXME: for now implemented full refund
        RefundType refundType = RefundType.full;

        PaymentEntity payment = paymentRepository.findOneByPaymentId(refundRequest.getPaymentId());
        if (payment == null) {
            log.error("payment {} not found", refundRequest.getPaymentId());
            throw new PaymentNotFoundException(refundRequest.getPaymentId());
        }

        JSONObject refundResponse;
        try {
            JSONObject requestRequest = new JSONObject();
            requestRequest.put("speed", RefundSpeed.optimum);
            if (refundType.equals(RefundType.partial)) {
                requestRequest.put("amount", payment.getAmount());
            }

            Refund refund = razorpayClient.Payments.refund(refundRequest.getPaymentId(), requestRequest);
            refundResponse = refund.toJson();
        } catch (RazorpayException e) {
            log.error("failed to create refund request");
            throw new RefundException(e.getMessage());
        }

        RefundEntity refund = new RefundEntity();
        refund.setType(refundType);
        refund.setOrderId(refundRequest.getOrderId());
        refund.setPaymentId(refundRequest.getPaymentId());
        refund.setReason(refundRequest.getReason());
        refund.setAmount(payment.getAmount());
        refund.setRefundAmount(refundResponse.get("amount"));
        refund.setRefundId(refundResponse.getString("id"));
        // refund.setStatus(RefundStatus.valueOf(refundResponse.get("status").toString()));
        refund.setStatus(RefundStatus.processed);
        refund = refundRepository.save(refund);

        log.info("updating payment {} status as {}", refundRequest.getPaymentId(), PaymentStatus.refunded);
        paymentService.updatePaymentStatus(refundRequest.getPaymentId(), PaymentStatus.refunded, userEntity);

        log.info("updating payment {} status as {}", refundRequest.getOrderId(), OrderStatus.refunded);
        orderService.updateOrderStatus(refundRequest.getOrderId(), OrderStatus.refunded, userEntity);

        log.info("updating user course {} status as {}", refundRequest.getProductId(), UserCourseStatus.refunded);
        userCourseService.updateUserCourseStatus(refundRequest.getProductId(), UserCourseStatus.refunded, userEntity);

        RefundResponse response = new RefundResponse();
        response.setRefundId(refund.getRefundId());
        response.setAmount(refund.getAmount());
        response.setRefundAmount(refund.getRefundAmount());
        response.setStatus(refund.getStatus());
        return response;
    }
}
