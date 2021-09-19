package com.gapfyl.exceptions.purchases.payments;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

public class PaymentNotFoundException extends GapFylException {
    public PaymentNotFoundException(String paymentId) {
        super(ErrorCode.PAYMENT_NOT_FOUND, "payment.not_found", new Object[] { paymentId });
    }
}
