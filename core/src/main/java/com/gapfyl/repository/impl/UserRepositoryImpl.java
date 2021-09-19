package com.gapfyl.repository.impl;

import com.gapfyl.constants.Collections;
import com.gapfyl.dto.users.ChangePasswordRequest;
import com.gapfyl.dto.users.UserUpdateRequest;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.filter.users.UserFilterCriteria;
import com.gapfyl.models.users.Status;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomUserRepository;
import com.gapfyl.util.Common;
import com.mongodb.DBRef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author vignesh
 * Created on 03/05/21
 **/

@Slf4j
@Service
public class UserRepositoryImpl implements ICustomUserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> fetchUsers(UserFilterCriteria filterCriteria) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (filterCriteria.getUserIds() != null && !filterCriteria.getUserIds().isEmpty()) {
            criteria = criteria.and("id").in(filterCriteria.getUserIds());
        }

        if (filterCriteria.getRoles() != null && !filterCriteria.getRoles().isEmpty()) {
            criteria = criteria.and("roles").in(filterCriteria.getRoles());
        }

        if (filterCriteria.getActivated() != null) {
            criteria = criteria.and("activated").is(filterCriteria.getActivated());
        }

        query.addCriteria(criteria);

        log.debug("fetching users with query {}", query);
        return mongoTemplate.find(query, UserEntity.class);
    }

    @Override
    public void updateStatus(String userId, Status status, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));

        Update update = new Update();
        update.set("status", status);
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());
        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }

    @Override
    public void updateProfile(UserUpdateRequest updateRequest, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userEntity.getId()));

        Update update = new Update();
        update.set("firstName", updateRequest.getFirstName());
        update.set("lastName", updateRequest.getLastName());
        update.set("email", updateRequest.getEmail());
        update.set("mobile", updateRequest.getMobile());
        update.set("profile_image_url", updateRequest.getProfileImageUrl());
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        log.debug("update profile with query {} update {}", query, update);
        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }

    @Override
    public UserEntity updateProfileType(String userId, ProfileType newProfileType, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(userId);

        query.addCriteria(criteria);

        Update update = new Update();
        update.set("profile_type", newProfileType);

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, UserEntity.class);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userEntity.getId()));

        Update update = new Update();
        update.set("password", passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }
}
