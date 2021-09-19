package com.gapfyl.repository.impl;

import com.gapfyl.dto.discussions.DiscussionRequest;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.discussions.DiscussionEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomDiscussionRepository;
import com.mongodb.DBRef;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DiscussionRepositoryImpl implements ICustomDiscussionRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<DiscussionEntity> fetchCategoryDiscussions(CategoryType categoryType, String categoryId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category.type").is(categoryType).and("category.id").is(categoryId));

        return mongoTemplate.find(query, DiscussionEntity.class);
    }

    @Override
    public List<DiscussionEntity> fetchUserCategoryDiscussions(CategoryType categoryType, String categoryId, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("category.type").is(categoryType)
                .and("category.id").is(categoryId).and("created_by.$id").is(new ObjectId(userEntity.getId()) );
        query.addCriteria(criteria);
        log.debug("fetch discussions: user {} category {} {}", userEntity.getId(), categoryType, categoryId);

        log.debug("fetching user discussions with query: {}", query);
        return mongoTemplate.find(query, DiscussionEntity.class);
    }

    @Override
    public UpdateResult updateDiscussion(String discussionId, DiscussionRequest discussionRequest, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(discussionId));

        Update update = new Update();
        update.set("title", discussionRequest.getTitle());
        update.set("description", discussionRequest.getDescription());
        update.set("tags", discussionRequest.getTags());

        log.info("updating discussion: query {} update {}", query, update);
        return mongoTemplate.updateFirst(query, update, DiscussionEntity.class);
    }
}
