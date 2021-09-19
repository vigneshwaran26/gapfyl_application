package com.gapfyl.exceptions.assets;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 23/04/21
 **/

public class ReportUploadFailedException extends GapFylException {

    public ReportUploadFailedException(String fileName, Throwable e) {
        super(ErrorCode.UPLOAD_FAILED, "user.upload.failed", new Object[]{ fileName });
        addSuppressed(e);
    }
}
