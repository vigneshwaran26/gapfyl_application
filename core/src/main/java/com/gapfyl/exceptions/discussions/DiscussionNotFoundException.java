package com.gapfyl.exceptions.discussions;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 06/05/21
 **/

public class DiscussionNotFoundException extends GapFylException {
    public DiscussionNotFoundException(String discussionId) {
        super(ErrorCode.DISCUSSION_NOT_FOUND, "discussion.not_found", new Object[]{ discussionId });
    }
}
