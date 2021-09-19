package com.gapfyl.models.users;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.Size;


@Data
@TypeAlias("registrations")
@Document(collection = Collections.REGISTRATIONS)
@EqualsAndHashCode(callSuper=false)
public class RegistrationEntity extends BaseAuditEntity {

    @Field("email")
    @Indexed(unique = true)
    @Size(max = 80, message = "Size.user.Email")
    private String email;

    @Field("activation_token")
    private String activationToken;

    @Field("is_activated")
    private boolean isActivated;
}
