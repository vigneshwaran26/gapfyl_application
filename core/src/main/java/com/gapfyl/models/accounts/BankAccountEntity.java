package com.gapfyl.models.accounts;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class BankAccountEntity {

    @Field("id")
    private ObjectId id;

    @Field("name")
    private String name;

    @Field("ifsc")
    private String ifsc;

    @Field("account_number")
    private String accountNumber;

}
