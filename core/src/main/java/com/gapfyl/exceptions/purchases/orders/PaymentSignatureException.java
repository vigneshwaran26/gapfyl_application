package com.gapfyl.exceptions.purchases.orders;

import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

public class PaymentSignatureException extends GapFylException {
    public PaymentSignatureException(String message) {
        super(34, message);
    }
}
