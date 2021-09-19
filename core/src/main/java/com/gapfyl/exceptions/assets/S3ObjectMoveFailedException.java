package com.gapfyl.exceptions.assets;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 23/04/21
 **/

public class S3ObjectMoveFailedException extends GapFylException {

    public S3ObjectMoveFailedException(String source, String destination, Throwable ex) {
        super(ErrorCode.MOVE_FAILED, "user.move.failed", new Object[]{ source, destination });
        addSuppressed(ex);
    }
}
