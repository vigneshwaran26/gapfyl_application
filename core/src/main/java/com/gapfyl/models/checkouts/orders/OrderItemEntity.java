package com.gapfyl.models.checkouts.orders;

import com.gapfyl.enums.common.Currency;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.products.ProductEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Getter
@Setter
public class OrderItemEntity {

    @Field("amount")
    public Double amount;

    @Field("currency")
    public Currency currency;

    @DBRef
    @Field("product")
    public ProductEntity product;

    @DBRef
    @Field("creator")
    public CreatorEntity creator;

}
