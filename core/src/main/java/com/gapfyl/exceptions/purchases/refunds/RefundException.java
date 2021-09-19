package com.gapfyl.exceptions.purchases.refunds;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

public class RefundException extends GapFylException {
    public RefundException(String message) {
        super(ErrorCode.REFUND_FAILED, message);
    }
}
