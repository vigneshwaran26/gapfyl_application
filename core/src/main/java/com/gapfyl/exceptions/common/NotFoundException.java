package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

public class NotFoundException extends GapFylException {
    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }

    public NotFoundException(String message, Object[] arguments) {
        super(ErrorCode.NOT_FOUND, message, arguments);
    }
}
