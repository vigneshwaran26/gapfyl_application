package com.gapfyl.models.geodata;

import com.gapfyl.constants.Collections;
import com.gapfyl.models.common.BaseAuditEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = Collections.GEODATA)
@EqualsAndHashCode(callSuper = false)
public class GeodataEntity extends BaseAuditEntity {

    @Field("ipv4")
    private int IPv4;

    @Field("city")
    private String City;

    @Field("country_code")
    private String CountryCode;

    @Field("country_name")
    private String CountryName;

    @Field("Latitude")
    private int Latitude;

    @Field("Longitude")
    private int Longitude;

    @Field("Postal")
    private int Postal;

    @Field("State")
    private String State;


}
