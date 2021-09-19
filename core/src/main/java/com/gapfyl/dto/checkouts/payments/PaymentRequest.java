package com.gapfyl.dto.checkouts.payments;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

@Getter
@Setter
public class PaymentRequest {

    @NotNull
    Double amount;

    @NotNull
    Currency currency;

    String receipt;

    Map<String, Object> notes;

    @NotBlank
    String orderId;

    @NotBlank
    String paymentId;

    @NotBlank
    String paymentOrderId;

    @NotBlank
    String signature;
}
