package com.gapfyl.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author vignesh
 * Created on 30/07/21
 **/

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    private String profileImageUrl;

}
