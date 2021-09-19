package com.gapfyl.dto.payouts;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class VPAAccountRequest {

    @NotBlank
    public String address;
}
