package com.gapfyl.models.common;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Getter
@Setter
public class PricingEntity {

    @Field("currency")
    public Currency currency;

    @Field("amount")
    public Double amount;

}
