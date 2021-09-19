package com.gapfyl.exceptions.users;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 13/05/21
 **/

public class UserInvalidRecoveryToken extends GapFylException {
    public UserInvalidRecoveryToken(String recoveryToken) {
        super(ErrorCode.USER_INVALID_RECOVERY_TOKEN, "user.invalid.recovery_token", new Object[] { recoveryToken });
    }
}
