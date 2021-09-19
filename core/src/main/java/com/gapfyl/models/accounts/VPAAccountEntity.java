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
public class VPAAccountEntity {

    @Field("id")
    private ObjectId id;

    @Field("address")
    private String address;

}
