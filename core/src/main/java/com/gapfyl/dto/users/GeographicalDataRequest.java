package com.gapfyl.dto.users;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GeographicalDataRequest {
    @NotBlank
    private int Ipv4;

    @NotBlank
    private String City;

    @NotBlank
    private String CountryCode;

    @NotBlank
    private String CountryName;

    @NotBlank
    private int Latitude;

    @NotBlank
    private int Longitude;

    @NotBlank
    private int postal;

    @NotBlank
    private String State;

}
