package com.gapfyl.models.courses;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.common.CreatorAccess;
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.common.CollaborationRequestEntity;
import com.gapfyl.models.common.PricingEntity;
import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.models.users.creators.CreatorEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Data
@TypeAlias("courses")
@Document(collection = Collections.COURSES)
@EqualsAndHashCode(callSuper = false)
public class CourseEntity extends BaseAuditEntity {

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("language")
    private String language;

    @Field("category")
    private String category;

    @Field("sub_category")
    private String subCategory;

    @Field("tags")
    private List<String> tags;

    @Field("status")
    private CourseStatus status;

    @Field("pricing")
    private PricingEntity pricing;

    @Field("is_active")
    private boolean isActive;

    @Field("creators")
    private List<String> creators;

    @Field("thumbnail_url")
    private String thumbnailUrl;

    @Field("preview_url")
    private String previewUrl;

    @DBRef
    @Field("contents")
    private List<ContentEntity> contents;

    @Field("collaborators")
    private List<String> collaborators;

    @Field("creator_accesses")
    private Map<String, List<CreatorAccess>> creatorAccesses;

    @Field("collaboration_requests")
    private List<CollaborationRequestEntity> collaborationRequests;

    @Field("avg_ratings")
    private Double avgRatings = 0.0;

    @Field("num_of_ratings")
    private int numOfRatings = 0;

}
