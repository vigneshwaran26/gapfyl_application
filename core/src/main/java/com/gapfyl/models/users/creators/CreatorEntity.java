package com.gapfyl.models.users.creators;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author vignesh
 * Created on 01/07/21
 **/

@Data
@Document(collection = Collections.CREATORS)
@EqualsAndHashCode(callSuper = false)
public class CreatorEntity extends BaseAuditEntity {

    @Field("account_id")
    public String accountId;

    @Field("user_id")
    public String userId;

    @Field("about")
    public String about;

    @Field("expertise")
    public List<String> expertise;

    @Field("interests")
    public List<String> interests;

    @Field("languages")
    public List<String> languages;

    @Field("educations")
    public List<EducationEntity> educations;

    @Field("works")
    public List<WorkEntity> works;

    @Field("linkedin_url")
    public String linkedInUrl;

    @Field("status")
    public CreatorStatus status;

}
