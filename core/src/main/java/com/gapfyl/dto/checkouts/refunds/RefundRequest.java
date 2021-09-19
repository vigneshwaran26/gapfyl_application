package com.gapfyl.dto.checkouts.refunds;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

@Getter
@Setter
public class RefundRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String paymentId;

    @NotBlank
    private String productId;

    private String reason;
}
