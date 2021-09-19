package com.gapfyl.exceptions.contents;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 05/05/21
 **/

public class ContentNotRemovedException extends GapFylException {
    public ContentNotRemovedException(String contentId) {
        super(ErrorCode.COURSE_CONTENT_NOT_REMOVED, "content.not_removed", new Object[] { contentId });
    }
}
