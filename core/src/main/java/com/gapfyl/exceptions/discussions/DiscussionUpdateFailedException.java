package com.gapfyl.exceptions.discussions;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 06/05/21
 **/

public class DiscussionUpdateFailedException extends GapFylException {
    public DiscussionUpdateFailedException(String discussionId) {
        super(ErrorCode.DISCUSSION_UPDATE_FAILED, "discussion.update.failed", new Object[]{ discussionId });
    }
}
