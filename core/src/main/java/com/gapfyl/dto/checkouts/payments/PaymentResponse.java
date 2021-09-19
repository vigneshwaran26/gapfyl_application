package com.gapfyl.dto.checkouts.payments;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

@Getter
@Setter
public class PaymentResponse {

    public String id;

    public Double amount;

    public Currency currency;

    public String orderId;

    public String paymentId;

    public String paymentOrderId;

}
