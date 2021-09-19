package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 16/04/21
 **/

public class UserNotActivatedException extends GapFylException {
    public UserNotActivatedException(String id) {
        super(ErrorCode.USER_NOT_ACTIVATED, "user.not_activated", new Object[] {id});
    }
}
