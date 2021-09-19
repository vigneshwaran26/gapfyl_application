package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

public class UserNotFoundException extends GapFylException {
    public UserNotFoundException(String id) {
        super(ErrorCode.USER_NOT_FOUND, "user.not_found", new Object[] { id });
    }
}
