package com.gapfyl.models.ratings;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.common.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 01/08/21
 **/

@Data
@Document(collection = Collections.RATINGS)
@EqualsAndHashCode(callSuper = false)
public class RatingsEntity extends BaseAuditEntity {

    @Field("category")
    private CategoryEntity category;

    @Field("ratings")
    private int ratings;

}
