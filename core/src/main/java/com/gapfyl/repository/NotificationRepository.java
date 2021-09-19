package com.gapfyl.repository;

import com.gapfyl.models.notifications.NotificationEntity;
import com.gapfyl.repository.custom.ICustomNotificationRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<NotificationEntity, String>, ICustomNotificationRepository {

}
