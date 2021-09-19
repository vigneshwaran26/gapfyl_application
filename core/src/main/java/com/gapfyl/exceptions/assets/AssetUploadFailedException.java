package com.gapfyl.exceptions.assets;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 26/04/21
 **/

public class AssetUploadFailedException extends GapFylException {

    public AssetUploadFailedException(String fileName, Throwable ex) {
        super(ErrorCode.UPLOAD_FAILED, "user.upload.failed", new Object[]{ fileName });
        addSuppressed(ex);
    }
}
