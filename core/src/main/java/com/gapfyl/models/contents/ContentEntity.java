package com.gapfyl.models.contents;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.enums.common.FileType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Data
@TypeAlias("contents")
@Document(collection = Collections.CONTENTS)
@EqualsAndHashCode(callSuper = false)
public class ContentEntity extends BaseAuditEntity {

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("content_type")
    private FileType contentType;

    @Field("content_url")
    private String contentUrl;

    @Field("thumbnail_url")
    private String thumbnailUrl;
}
