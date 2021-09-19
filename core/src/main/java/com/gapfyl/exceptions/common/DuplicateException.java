package com.gapfyl.exceptions.common;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

public class DuplicateException extends GapFylException {
    public DuplicateException(String id) {
        super(ErrorCode.DUPLICATE_REQUEST, "duplicate.request", new Object[] {id});
    }
}
