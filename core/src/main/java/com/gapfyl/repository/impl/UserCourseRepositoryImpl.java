package com.gapfyl.repository.impl;

import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.models.users.UserCourseEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomUserCourseRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 01/08/21
 **/

@Service
public class UserCourseRepositoryImpl implements ICustomUserCourseRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<UserCourseEntity> fetchUserCourses(UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userEntity.getId())
                .and("status").is(UserCourseStatus.purchased.name()));
        return mongoTemplate.find(query, UserCourseEntity.class);
    }

    @Override
    public UpdateResult updateUserCourseStatus(String courseId, UserCourseStatus status, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userEntity.getId()).and("course_id").is(courseId));

        Update update = new Update();
        update.set("status", status);
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, UserCourseEntity.class);
    }
}
