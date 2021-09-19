package com.gapfyl.models.users;

import com.gapfyl.models.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * @author vignesh
 * Created on 27/04/21
 **/

@Getter
@Setter
public class AccountScopedEntity extends BaseEntity {

    @DBRef
    AccountEntity account;
}
