package com.gapfyl.repository.impl;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.models.ratings.RatingsEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomRatingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

@Slf4j
@Service
public class RatingsRepositoryImpl implements ICustomRatingsRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public RatingsEntity fetchUserCategoryRatings(CategoryType categoryType, String categoryId, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("category.type").is(categoryType)
                .and("category.id").is(categoryId).and("user.id").is(userEntity.getId());
        query.addCriteria(criteria);
        log.debug("fetching user category ratings with query {}", query);
        return mongoTemplate.findOne(query, RatingsEntity.class);
    }

    @Override
    public List<RatingsEntity> fetchCategoryRatings(CategoryType categoryType, String categoryId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("category.type").is(categoryType).and("category.id").is(categoryId);
        query.addCriteria(criteria);
        log.debug("fetching category ratings with query {}", query);
        return mongoTemplate.find(query, RatingsEntity.class);

    }
}
