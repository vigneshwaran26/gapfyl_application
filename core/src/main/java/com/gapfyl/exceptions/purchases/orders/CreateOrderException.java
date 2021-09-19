package com.gapfyl.exceptions.purchases.orders;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

public class CreateOrderException extends GapFylException {
    public CreateOrderException(String message) {
        super(ErrorCode.CREATE_ORDER_FAILED_ERROR, message);
    }
}
