package com.gapfyl.exceptions.creators;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 20/07/21
 **/

public class InvalidCreatorRequestException extends GapFylException {
    public InvalidCreatorRequestException(String message) {
        super(ErrorCode.CREATOR_INVALID_REQUEST, message);
    }
}
