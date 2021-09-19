package com.gapfyl.dto.products;

import com.gapfyl.dto.common.Base;
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.products.ProductType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 29/07/21
 **/

@Getter
@Setter
public class ProductResponse extends Base {

    public String productId;

    public String creatorId;

    public String name;

    public String description;

    public String category;

    public String subCategory;

    public ProductType type;

    public Double amount;

    public Currency currency;

    public boolean free;

    public boolean active;

}
