package com.gapfyl.repository.impl;

import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomContentRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author vignesh
 * Created on 01/05/21
 **/

public class ContentRepositoryImpl implements ICustomContentRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public UpdateResult updateContent(String contentId, ContentRequest contentRequest, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(contentId));
        Update update = new Update();
        update.set("title", contentRequest.getTitle());
        update.set("description", contentRequest.getDescription());
        update.set("content_type", contentRequest.getContentType());
        update.set("content_url", contentRequest.getContentUrl());
        update.set("modified_date", Common.getCurrentUTCDate());
        update.set("modified_by", userEntity);
        return mongoTemplate.updateFirst(query, update, ContentEntity.class);
    }
}
