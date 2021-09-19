package com.gapfyl.exceptions.purchases.orders;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

public class OrderNotFoundException extends GapFylException {
    public OrderNotFoundException(String orderId) {
        super(ErrorCode.ORDER_NOT_FOUND, "order.not_found", new Object[] { orderId });
    }
}
