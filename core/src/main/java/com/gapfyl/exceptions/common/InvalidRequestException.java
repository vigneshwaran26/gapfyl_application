package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

public class InvalidRequestException extends GapFylException {
    public InvalidRequestException(String message) {
        super(ErrorCode.INVALID_REQUEST, message);
    }

    public InvalidRequestException(String message, Object[] arguments) {
        super(ErrorCode.INVALID_REQUEST, message, arguments);
    }
}
