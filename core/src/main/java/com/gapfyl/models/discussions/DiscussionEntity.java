package com.gapfyl.models.discussions;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.common.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


@Data
@TypeAlias("discussions")
@Document(collection = Collections.DISCUSSIONS)
@EqualsAndHashCode(callSuper = false)
public class DiscussionEntity extends BaseAuditEntity {

    @Field("title")
    private String title;

    @Field("text")
    private String description;

    @Field("tags")
    private List<String> tags;

    @Field("category")
    private CategoryEntity category;

    @DBRef(lazy = true)
    @Field("replies")
    private List<DiscussionEntity> replies;

}
