package com.gapfyl.services.checkouts.payments;

import com.gapfyl.dto.checkouts.payments.PaymentRequest;
import com.gapfyl.dto.checkouts.payments.PaymentResponse;
import com.gapfyl.dto.checkouts.payments.VerifyPaymentSignature;
import com.gapfyl.enums.purchase.payments.PaymentStatus;
import com.gapfyl.exceptions.purchases.orders.PaymentSignatureException;
import com.gapfyl.models.checkouts.payments.PaymentEntity;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest paymentRequest, UserEntity userEntity);

    PaymentEntity fetchPaymentById(String id);

    PaymentEntity fetchPaymentByPaymentId(String paymentId);

    void updatePaymentStatus(String id, PaymentStatus status, UserEntity userEntity);

    boolean verifyPayment(VerifyPaymentSignature verifyPaymentSignature, UserEntity userEntity)
            throws PaymentSignatureException;
}
