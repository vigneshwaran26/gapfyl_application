package com.gapfyl.repository.impl;

import com.gapfyl.dto.users.learners.LearnerRequest;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.learners.LearnerEntity;
import com.gapfyl.repository.custom.ICustomLearnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author vignesh
 * Created on 15/08/21
 **/

@Slf4j
@Service
public class LearnerRepositoryImpl implements ICustomLearnerRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public LearnerEntity updateLearner(String learnerId, LearnerRequest learnerRequest, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(learnerId).and("user_id").is(userEntity.getId());

        query.addCriteria(criteria);

        Update update = new Update();
        update.set("about", learnerRequest.getAbout());
        update.set("languages", learnerRequest.getLanguages());
        update.set("interests", learnerRequest.getInterests());

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, LearnerEntity.class);
    }
}
