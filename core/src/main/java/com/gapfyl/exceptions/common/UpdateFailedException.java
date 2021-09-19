package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 06/05/21
 **/

public class UpdateFailedException extends GapFylException {
    public UpdateFailedException(String message) {
        super(ErrorCode.UPDATE_FAILED, message);
    }

    public UpdateFailedException(String message, Object[] arguments) {
        super(ErrorCode.UPDATE_FAILED, message, arguments);
    }
}
