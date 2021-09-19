package com.gapfyl.models.common;

import com.gapfyl.enums.common.CategoryType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 22/04/21
 **/

@Getter
@Setter
public class CategoryEntity {

    @Field("id")
    private String id;

    @Field("type")
    private CategoryType type;

}
