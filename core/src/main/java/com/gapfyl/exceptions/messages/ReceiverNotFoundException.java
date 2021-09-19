package com.gapfyl.exceptions.messages;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

/**
 * @author vignesh
 * Created on 18/05/21
 **/

public class ReceiverNotFoundException extends GapFylException {
    public ReceiverNotFoundException(String id) {
        super(ErrorCode.MESSAGE_RECEIVER_NOT_FOUND, "message.receiver.not_found", new Object[] { id });
    }
}
