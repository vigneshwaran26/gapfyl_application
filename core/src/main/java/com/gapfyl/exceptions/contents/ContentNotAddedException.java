package com.gapfyl.exceptions.contents;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 05/05/21
 **/

public class ContentNotAddedException extends GapFylException {
    public ContentNotAddedException(String contentName) {
        super(ErrorCode.COURSE_CONTENT_NOT_ADDED, "content.not_added", new Object[]{ contentName });
    }
}
