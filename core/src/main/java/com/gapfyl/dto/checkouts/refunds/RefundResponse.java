package com.gapfyl.dto.checkouts.refunds;

import com.gapfyl.enums.purchase.refunds.RefundStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 04/07/21
 **/

@Getter
@Setter
public class RefundResponse {

    public String refundId;

    public Double amount;

    public Object refundAmount;

    public RefundStatus status;

}
