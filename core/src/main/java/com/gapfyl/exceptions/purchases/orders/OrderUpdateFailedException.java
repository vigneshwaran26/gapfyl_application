package com.gapfyl.exceptions.purchases.orders;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

public class OrderUpdateFailedException extends GapFylException {
    public OrderUpdateFailedException(String orderId) {
        super(ErrorCode.ORDER_UPDATE_FAILED, "order.updated.failed", new Object[] { orderId });
    }
}
