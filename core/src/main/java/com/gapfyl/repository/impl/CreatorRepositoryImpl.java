package com.gapfyl.repository.impl;

import com.gapfyl.dto.users.creators.CreatorRequest;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.creators.EducationEntity;
import com.gapfyl.models.users.creators.WorkEntity;
import com.gapfyl.repository.custom.ICustomCreatorRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Slf4j
@Service
public class CreatorRepositoryImpl implements ICustomCreatorRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public CreatorEntity updateCreator(String creatorId, CreatorRequest creatorRequest,
                                       List<EducationEntity> educations, List<WorkEntity> works,
                                       UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(creatorId).and("user_id").is(userEntity.getId());
        query.addCriteria(criteria);

        Update update = new Update();
        update.set("about", creatorRequest.getAbout());
        update.set("languages", creatorRequest.getLanguages());
        update.set("expertise", creatorRequest.getExpertise());
        update.set("interests", creatorRequest.getInterests());
        update.set("linkedProfile", creatorRequest.getLinkedProfile());
        update.set("profileUrl", creatorRequest.getProfileUrl());
        update.set("educations", educations);
        update.set("works", works);

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, CreatorEntity.class);

    }

    @Override
    public UpdateResult updateRazorpayContactId(String email, String contactId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        Update update = new Update();
        update.set("razorpay.contact_id", contactId);
        return mongoTemplate.updateFirst(query, update, CreatorEntity.class);
    }

    @Override
    public UpdateResult updateRazorpayAccountId(String email, String accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        Update update = new Update();
        update.set("razorpay.account_id", accountId);
        return mongoTemplate.updateFirst(query, update, CreatorEntity.class);
    }
}
