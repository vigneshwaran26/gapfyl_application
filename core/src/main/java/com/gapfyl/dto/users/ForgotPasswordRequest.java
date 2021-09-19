package com.gapfyl.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 13/05/21
 **/

@Getter
@Setter
public class ForgotPasswordRequest {

    @NotBlank
    private String email;

}
