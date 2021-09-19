package com.gapfyl.services.checkouts.payments;

import com.gapfyl.dto.checkouts.payments.PaymentRequest;
import com.gapfyl.dto.checkouts.payments.PaymentResponse;
import com.gapfyl.dto.checkouts.payments.VerifyPaymentSignature;
import com.gapfyl.enums.purchase.payments.PaymentStatus;
import com.gapfyl.exceptions.purchases.orders.PaymentSignatureException;
import com.gapfyl.models.checkouts.payments.PaymentEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.PaymentRepository;
import com.gapfyl.services.checkouts.orders.OrderService;
import com.gapfyl.util.Common;
import com.gapfyl.util.Signature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SignatureException;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Value("${razorpay.key_secret:no-secret}")
    String razorpaySecret;

    @Autowired
    OrderService orderService;

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest, UserEntity userEntity) {
        log.info("storing payment details {}", paymentRequest);
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setVerified(false);
        paymentEntity.setAmount(paymentRequest.getAmount());
        paymentEntity.setCurrency(paymentRequest.getCurrency());
        paymentEntity.setOrderId(paymentRequest.getOrderId());
        paymentEntity.setPaymentId(paymentRequest.getPaymentId());
        paymentEntity.setPaymentOrderId(paymentRequest.getPaymentOrderId());
        paymentEntity.setSignature(paymentRequest.getSignature());
        paymentEntity.setCreatedDate(Common.getCurrentUTCDate());
        paymentEntity.setModifiedDate(Common.getCurrentUTCDate());
        paymentEntity = paymentRepository.save(paymentEntity);
        log.info("payment details stored");

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(paymentEntity.getId());
        paymentResponse.setOrderId(paymentEntity.getOrderId());
        paymentResponse.setPaymentId(paymentEntity.getPaymentId());
        paymentResponse.setPaymentOrderId(paymentEntity.getPaymentOrderId());
        paymentResponse.setAmount(paymentEntity.getAmount());
        paymentResponse.setCurrency(paymentEntity.getCurrency());
        return paymentResponse;
    }

    @Override
    public PaymentEntity fetchPaymentById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentEntity fetchPaymentByPaymentId(String paymentId) {
        return paymentRepository.findOneByPaymentId(paymentId);
    }

    @Override
    public void updatePaymentStatus(String paymentId, PaymentStatus status, UserEntity userEntity) {
        log.info("user {} [{}] updating payment status as {}", userEntity.getName(), userEntity.getId(), status);
        PaymentEntity paymentEntity = fetchPaymentByPaymentId(paymentId);
        if (paymentEntity == null) {
            log.error("payment {} is not found", paymentId);
            return;
        }

        paymentEntity.setStatus(status);
        paymentEntity.setModifiedBy(userEntity);
        paymentEntity.setModifiedDate(Common.getCurrentUTCDate());
        paymentRepository.save(paymentEntity);
        log.info("user {} [{}] updated payment status as {}", userEntity.getName(), userEntity.getId(), status);
    }

    private void verifyPayment(String paymentId, UserEntity userEntity) {
        log.info("verifying payment {}", paymentId);
        PaymentEntity paymentEntity = paymentRepository.findOneByPaymentId(paymentId);
        if (paymentEntity == null) {
            log.error("verifying payment, not found {}", paymentId);
            return;
        }

        paymentEntity.setVerified(true);
        paymentEntity.setModifiedBy(userEntity);
        paymentEntity.setModifiedDate(Common.getCurrentUTCDate());
        paymentRepository.save(paymentEntity);
        log.info("payment verified {}", paymentId);
    }

    @Override
    public boolean verifyPayment(VerifyPaymentSignature verifyPaymentSignature, UserEntity userEntity)
            throws PaymentSignatureException {
        try {
            log.info("verifying payment signature {} - {}", verifyPaymentSignature.getPaymentId(),
                    verifyPaymentSignature.getSignature());

            String generatedSignature = Signature.calculateRFC210HMAC(verifyPaymentSignature.getOrderId() + "|" +
                    verifyPaymentSignature.getPaymentId(), razorpaySecret);
            if (generatedSignature.equals(verifyPaymentSignature.getSignature())) {
                log.info("payment signature verified {} - {}", verifyPaymentSignature.getPaymentId(), verifyPaymentSignature.getSignature());
                verifyPayment(verifyPaymentSignature.getPaymentId(), userEntity);
                return true;
            }
            return false;
        } catch (SignatureException e) {
            log.error("failed to verify signature: {}", e.getMessage());
            throw new PaymentSignatureException(e.getMessage());
        }
    }
}
