package com.gapfyl.models.checkouts.refunds;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.purchase.refunds.RefundStatus;
import com.gapfyl.enums.purchase.refunds.RefundType;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 02/07/21
 **/

@Data
@Document(collection = Collections.REFUNDS)
@EqualsAndHashCode(callSuper = false)
public class RefundEntity extends BaseAuditEntity {

    @Field("order_id")
    public String orderId;

    @Field("payment_id")
    public String paymentId;

    @Field("refund_id")
    public String refundId;

    @Field("amount")
    public Double amount;

    @Field("refund_amount")
    public Object refundAmount;

    @Field("status")
    public RefundStatus status;

    @Field("type")
    public RefundType type;

    @Field("reason")
    public String reason;

}
