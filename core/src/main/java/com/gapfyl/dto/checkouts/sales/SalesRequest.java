package com.gapfyl.dto.checkouts.sales;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@Getter
@Setter
public class SalesRequest {

    @NotBlank
    private String orderId;

    @NotBlank
    private String paymentId;

    @NotNull
    private List<String> productIds;
}
