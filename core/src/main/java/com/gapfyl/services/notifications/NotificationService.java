package com.gapfyl.services.notifications;

import com.gapfyl.dto.notifications.Notification;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notificationDTO, UserEntity userEntity);

    List<Notification> fetchUserNotifications(UserEntity userEntity);

}
