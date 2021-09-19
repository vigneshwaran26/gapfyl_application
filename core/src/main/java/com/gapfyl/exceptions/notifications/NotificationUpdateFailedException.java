package com.gapfyl.exceptions.notifications;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

public class NotificationUpdateFailedException extends GapFylException {
    public NotificationUpdateFailedException(String notificationId){
        super(ErrorCode.NOTIFICATION_UPDATE_FAILED, "notification.update.failed", new Object[]{ notificationId });
    }
}
