package com.gapfyl.repository.impl;

import com.gapfyl.models.events.EventEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class EventRepositoryImpl implements ICustomEventRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<EventEntity> fetchEvents(UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(userEntity.getId()));
        return mongoTemplate.find(query, EventEntity.class);
    }
}
