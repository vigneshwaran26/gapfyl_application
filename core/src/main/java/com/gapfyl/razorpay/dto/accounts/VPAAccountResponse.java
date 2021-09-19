package com.gapfyl.razorpay.dto.accounts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class VPAAccountResponse {

    @Field("username")
    public String username;

    @Field("handle")
    public String handle;

    @Field("address")
    public String address;
}
