package com.gapfyl.repository;

import com.gapfyl.models.events.EventEntity;
import com.gapfyl.repository.custom.ICustomEventRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventEntity, String>, ICustomEventRepository {

}


