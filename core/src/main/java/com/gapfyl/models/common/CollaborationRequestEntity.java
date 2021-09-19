package com.gapfyl.models.common;

import com.gapfyl.enums.common.CollaborationStatus;
import com.gapfyl.enums.common.CreatorAccess;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author vignesh
 * Created on 08/07/21
 **/

@Getter
@Setter
public class CollaborationRequestEntity {

    @Field("id")
    public String id;

    @Field("requester_id")
    public String requesterId;

    @Field("collaborator_id")
    public String collaboratorId;

    @Field("name")
    public String name;

    @Field("email")
    public String email;

    @Field("accesses")
    public List<CreatorAccess> accesses;

    @Field("status")
    public CollaborationStatus status;

}