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
public class ResetPasswordRequest {

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;

}
