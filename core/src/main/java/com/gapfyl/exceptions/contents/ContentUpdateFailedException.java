package com.gapfyl.exceptions.contents;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 05/05/21
 **/

public class ContentUpdateFailedException extends GapFylException {

    public ContentUpdateFailedException(String id) {
        super(ErrorCode.CONTENT_UPDATE_FAILED, "", new Object[] { id} );
    }
}
