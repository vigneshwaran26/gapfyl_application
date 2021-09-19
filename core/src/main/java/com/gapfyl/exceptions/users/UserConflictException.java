package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 16/04/21
 **/

public class UserConflictException extends GapFylException {
    public UserConflictException(String id) {
        super(ErrorCode.USER_CONFLICT, "user.conflict", new Object[] {id});
    }
}
