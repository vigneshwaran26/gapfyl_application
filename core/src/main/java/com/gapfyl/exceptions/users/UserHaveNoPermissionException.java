package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

public class UserHaveNoPermissionException extends GapFylException {
    public UserHaveNoPermissionException(String id) {
        super(ErrorCode.USER_HAVE_NO_PERMISSION,"user.have.no.permission",new Object[] {id});
    }
}
