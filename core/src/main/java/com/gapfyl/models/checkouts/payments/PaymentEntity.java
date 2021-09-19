package com.gapfyl.models.checkouts.payments;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.purchase.payments.PaymentStatus;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

@Data
@Document(collection = Collections.PAYMENTS)
@EqualsAndHashCode(callSuper = false)
public class PaymentEntity extends BaseAuditEntity {

    @Field("amount")
    public Double amount;

    @Field("currency")
    public Currency currency;

    @Field("order_id")
    public String orderId;

    @Field("payment_id")
    public String paymentId;

    @Field("payment_order_id")
    public String paymentOrderId;

    @Field("signature")
    public String signature;

    public boolean verified;

    public PaymentStatus status;
}
