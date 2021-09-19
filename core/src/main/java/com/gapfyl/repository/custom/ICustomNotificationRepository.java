package com.gapfyl.repository.custom;

import com.gapfyl.models.notifications.NotificationEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

public interface ICustomNotificationRepository {

    List<NotificationEntity> fetchNotifications(UserEntity userEntity);
}
