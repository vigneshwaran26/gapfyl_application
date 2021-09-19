package com.gapfyl.repository;

import com.gapfyl.models.messages.MessageEntity;
import com.gapfyl.repository.custom.ICustomMessageRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository <MessageEntity, String>, ICustomMessageRepository {
}
