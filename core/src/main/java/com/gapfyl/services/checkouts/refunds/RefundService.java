package com.gapfyl.services.checkouts.refunds;

import com.gapfyl.dto.checkouts.refunds.RefundRequest;
import com.gapfyl.dto.checkouts.refunds.RefundResponse;
import com.gapfyl.exceptions.purchases.payments.PaymentNotFoundException;
import com.gapfyl.exceptions.purchases.refunds.RefundException;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

public interface RefundService {

    RefundResponse requestRefund(RefundRequest refundRequest, UserEntity userEntity) throws PaymentNotFoundException, RefundException;

}
