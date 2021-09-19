package com.gapfyl.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 22/04/21
 **/

@Getter
@Setter
public class RegistrationRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    @NotBlank
    private String password;
}
