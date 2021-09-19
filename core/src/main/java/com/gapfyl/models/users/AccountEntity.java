package com.gapfyl.models.users;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 27/04/21
 **/

@Data
@TypeAlias("accounts")
@Document(collection = Collections.ACCOUNTS)
@EqualsAndHashCode(callSuper = false)
public class AccountEntity extends BaseEntity {

    @Field("name")
    private String name;
}
