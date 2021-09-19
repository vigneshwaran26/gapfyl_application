package com.gapfyl.exceptions.messages;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

public class MessageNotFoundException  extends GapFylException {
    public MessageNotFoundException(String messageId){
        super(ErrorCode.MESSAGE_NOT_FOUND, "message.not_found" ,new Object[]{messageId});
    }

}
