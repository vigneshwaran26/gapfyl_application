package com.gapfyl.models.checkouts.sales;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.purchase.sales.SaleStatus;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.checkouts.orders.OrderEntity;
import com.gapfyl.models.checkouts.payments.PaymentEntity;
import com.gapfyl.models.users.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 21/07/21
 **/

@Data
@Document(collection = Collections.SALES)
@EqualsAndHashCode(callSuper = false)
public class SalesEntity extends BaseAuditEntity {

    @Field("account_id")
    public String accountId;

    @Field("user_id")
    public String userId;

    @Field("order_id")
    public String orderId;

    @Field("payment_id")
    public String paymentId;

    @Field("status")
    public SaleStatus status;

}
