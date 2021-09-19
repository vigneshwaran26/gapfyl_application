package com.gapfyl.models.checkouts.orders;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.purchase.orders.OrderStatus;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Data
@Document(collection = Collections.ORDERS)
@EqualsAndHashCode(callSuper = false)
public class OrderEntity extends BaseAuditEntity {

    @Field("order_id")
    public String orderId;

    @Field("amount")
    public Double amount;

    @Field("currency")
    public Currency currency;

    @Field("status")
    public OrderStatus status;

    @Field("items")
    public List<OrderItemEntity> items;

}
