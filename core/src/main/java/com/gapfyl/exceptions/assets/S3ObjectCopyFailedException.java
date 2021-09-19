package com.gapfyl.exceptions.assets;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 23/04/21
 **/

public class S3ObjectCopyFailedException extends GapFylException {

    public S3ObjectCopyFailedException(String source, String destination) {
        super(ErrorCode.COPY_FAILED, "user.copy.failed", new Object[]{ source, destination });
    }
}
