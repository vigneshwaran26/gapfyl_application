package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

public class NoPermissionException extends GapFylException {
    public NoPermissionException(String message) {
        super(ErrorCode.NO_PERMISSION, message);
    }

    public NoPermissionException(String message, Object[] arguments) {
        super(ErrorCode.NO_PERMISSION, message, arguments);
    }
}
