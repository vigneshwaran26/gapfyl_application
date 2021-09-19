package com.gapfyl.dto.common;

import com.gapfyl.dto.users.UserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

@Getter
@Setter
public class Base {

    private String id;

    private UserResponse createdBy;

    private Date createdDate;

    private UserResponse modifiedBy;

    private Date modifiedDate;
}
