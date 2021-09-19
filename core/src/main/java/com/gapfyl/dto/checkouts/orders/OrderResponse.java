package com.gapfyl.dto.checkouts.orders;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class OrderResponse {

    public String id;

    public String orderId;

    public Double amount;

    public Currency currency;
}
