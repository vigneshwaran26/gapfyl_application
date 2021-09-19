package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 25/06/21
 **/

public class AccountNotFoundException extends GapFylException {
    public AccountNotFoundException(String message) {
        super(ErrorCode.USER_ACCOUNT_NOT_FOUND, message);
    }
}
