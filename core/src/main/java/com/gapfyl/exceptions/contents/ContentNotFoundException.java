package com.gapfyl.exceptions.contents;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 04/05/21
 **/

public class ContentNotFoundException extends GapFylException {
    public ContentNotFoundException(String id) {
        super(ErrorCode.CONTENT_NOT_FOUND, "content.not_found", new Object[] { id });
    }
}
