package com.gapfyl.exceptions.events;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

public class EventNotFoundException extends GapFylException {
    public  EventNotFoundException(String eventId){
        super(ErrorCode.EVENT_NOT_FOUND, "event.not_found", new Object[]{eventId});
    }
}
