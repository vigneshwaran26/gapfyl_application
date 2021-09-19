package com.gapfyl.exceptions.assets;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 23/04/21
 **/

public class S3ObjectDeleteFailedException extends GapFylException {

    public S3ObjectDeleteFailedException(String source) {
        super(ErrorCode.DELETE_FAILED, "user.delete.failed", new Object[]{ source });
    }
}
