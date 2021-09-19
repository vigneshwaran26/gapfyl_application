package com.gapfyl.filter.users;

import com.gapfyl.models.users.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 21/05/21
 **/

@Getter
@Setter
public class UserFilterCriteria {

    private List<String> userIds;
    private List<UserRole> roles;
    private Boolean activated;

}
