package com.gapfyl.models.common;

import com.gapfyl.models.users.AccountScopedEntity;
import com.gapfyl.models.users.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

@Getter
@Setter
public class BaseAuditEntity extends AccountScopedEntity {

    @DBRef
    @Field("created_by")
    private UserEntity createdBy;

    @Field("created_date")
    private Date createdDate;

    @DBRef
    @Field("modified_by")
    private UserEntity modifiedBy;

    @Field("modified_date")
    private Date modifiedDate;

}
