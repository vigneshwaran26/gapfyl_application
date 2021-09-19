package com.gapfyl.models.uploads;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.enums.common.FileType;
import com.gapfyl.models.common.CategoryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 25/06/21
 **/

@Data
@Document(collection = Collections.UPLOADS)
@EqualsAndHashCode(callSuper = false)
public class UploadEntity extends BaseAuditEntity {

    @Field("name")
    String name;

    @Field("file_type")
    FileType fileType;

    @Field("upload_key")
    String uploadKey;

    @Field("category")
    public CategoryEntity category;
}
