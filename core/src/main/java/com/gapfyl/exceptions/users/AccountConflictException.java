package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 14/05/21
 **/

public class AccountConflictException extends GapFylException {
    public AccountConflictException(String name) {
        super(ErrorCode.USER_ACCOUNT_CONFLICT, "user.account.conflict", new Object[]{ name });
    }
}
