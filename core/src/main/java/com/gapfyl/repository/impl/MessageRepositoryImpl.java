package com.gapfyl.repository.impl;

import com.gapfyl.models.messages.MessageEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomMessageRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/05/21
 **/

public class MessageRepositoryImpl implements ICustomMessageRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<MessageEntity> fetchMessages(UserEntity userEntity) {
        // FIXME: build a query to fetch user messages
        Query query = new Query();
        return  mongoTemplate.find(query, MessageEntity.class);
    }

    @Override
    public UpdateResult updateReadAt(String messageId, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(messageId));

        Update update = new Update();
        update.set("read_at", Common.getCurrentUTCDate());
        update.set("modified_at", Common.getCurrentUTCDate());
        update.set("modified_by", userEntity);
        return mongoTemplate.updateFirst(query, update, MessageEntity.class);
    }
}
