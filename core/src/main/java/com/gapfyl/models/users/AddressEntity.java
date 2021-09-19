package com.gapfyl.models.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class AddressEntity {

    @Field("place")
    private String place;

    @Field("city")
    private String city;

    @Field("state")
    private String state;

    @Field("country")
    private String country;

    @Field("postal_code")
    private String postalCode;

}
