package com.gapfyl.dto.checkouts.payments;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

@Getter
@Setter
public class VerifyPaymentSignature {

    @NotBlank
    String orderId;

    @NotBlank
    String paymentId;

    @NotBlank
    String signature;

}
