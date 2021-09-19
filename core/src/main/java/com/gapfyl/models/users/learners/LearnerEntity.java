package com.gapfyl.models.users.learners;

import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author vignesh
 * Created on 14/08/21
 **/

@Data
@Document(collection = "learners")
@EqualsAndHashCode(callSuper = false)
public class LearnerEntity extends BaseAuditEntity {

    @Field("account_id")
    private String accountId;

    @Field("user_id")
    private String userId;

    @Field("about")
    private String about;

    @Field("languages")
    private List<String> languages;

    @Field("interests")
    private List<String> interests;

}
