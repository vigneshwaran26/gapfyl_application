package com.gapfyl.dto.users;

import com.gapfyl.enums.users.ProfileType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Getter
@Setter
public class UserResponse {

    private String username;

    private String name;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private String profileImageUrl;

    private ProfileType profileType;

    private List<String> roles;

    private boolean activated;

    private AccountResponse account;
}
