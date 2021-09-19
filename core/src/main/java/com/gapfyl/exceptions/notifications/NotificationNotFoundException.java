package com.gapfyl.exceptions.notifications;

import com.gapfyl.constants.ErrorCode;
import com.gapfyl.exceptions.GapFylException;

public class NotificationNotFoundException extends GapFylException {
    public NotificationNotFoundException(String notificationId) {
        super(ErrorCode.NOTIFICATION_NOT_FOUND, "notification.not.found", new Object[]{ notificationId });

    }
}
