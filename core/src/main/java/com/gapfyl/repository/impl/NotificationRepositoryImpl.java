package com.gapfyl.repository.impl;

import com.gapfyl.models.notifications.NotificationEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class NotificationRepositoryImpl implements ICustomNotificationRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<NotificationEntity> fetchNotifications(UserEntity userEntity) {
        // FIXME: query to fetch user notifications
        Query query = new Query();
        return mongoTemplate.find(query, NotificationEntity.class);
    }
}
