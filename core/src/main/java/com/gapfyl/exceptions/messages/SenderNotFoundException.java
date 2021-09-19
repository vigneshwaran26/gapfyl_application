package com.gapfyl.exceptions.messages;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 18/05/21
 **/

public class SenderNotFoundException extends GapFylException {
    public SenderNotFoundException(String id) {
        super(ErrorCode.MESSAGE_SENDER_NOT_FOUND, "message.sender.not_found", new Object[] { id });
    }
}
