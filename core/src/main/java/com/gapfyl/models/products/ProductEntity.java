package com.gapfyl.models.products;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.products.ProductType;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.common.PricingEntity;
import com.gapfyl.models.users.creators.CreatorEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Data
@Document(collection = Collections.PRODUCTS)
@EqualsAndHashCode(callSuper = false)
public class ProductEntity extends BaseAuditEntity {

    @Field("product_id")
    public String productId;

    @Field("name")
    public String name;

    @Field("description")
    public String description;

    @Field("category")
    public String category;

    @Field("sub_category")
    public String subCategory;

    @Field("type")
    public ProductType type;

    @Field("creator")
    public CreatorEntity creator;

    @Field("pricing")
    public PricingEntity pricing;

    @Field("is_free")
    public boolean free;

    @Field("is_active")
    public boolean active;

}
