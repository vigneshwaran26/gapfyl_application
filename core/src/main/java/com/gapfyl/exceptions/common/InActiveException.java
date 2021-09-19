package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

public class InActiveException extends GapFylException {
    public InActiveException(String message) {
        super(ErrorCode.IN_ACTIVE, message);
    }

    public InActiveException(String message, Object[] arguments) {
        super(ErrorCode.IN_ACTIVE, message, arguments);
    }
}
