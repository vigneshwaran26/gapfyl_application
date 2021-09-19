package com.gapfyl.repository.impl;

import com.gapfyl.constants.Collections;
import com.gapfyl.dto.courses.CourseRequest;
import com.gapfyl.enums.common.CollaborationStatus;
import com.gapfyl.filter.courses.CourseFilterCriteria;
import com.gapfyl.models.common.PricingEntity;
import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomCourseRepository;
import com.gapfyl.util.Common;
import com.mongodb.DBRef;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Slf4j
public class CourseRepositoryImpl implements ICustomCourseRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<CourseEntity> fetchCreatorCourses(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("creators").is(userId));
        return mongoTemplate.find(query, CourseEntity.class);
    }

    @Override
    public List<CourseEntity> fetchCollaboratorCourses(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("collaborators").is(userId));
        return mongoTemplate.find(query, CourseEntity.class);
    }

    @Override
    public List<CourseEntity> fetchWithFilters(String queryText, CourseFilterCriteria filterCriteria) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (queryText != null) {
            criteria = criteria.regex(queryText, "i");
        }

        if (!Common.isNullOrEmpty(filterCriteria.getTags())) {
            criteria = criteria.and("tags").in(filterCriteria.getTags());
        }

        if (!Common.isNullOrEmpty(filterCriteria.getLanguages())) {
            criteria = criteria.and("language").in(filterCriteria.getLanguages());
        }

        if (!Common.isNullOrEmpty(filterCriteria.getCategories())) {
            criteria = criteria.and("category").in(filterCriteria.getCategories());
        }

        if (!Common.isNullOrEmpty(filterCriteria.getSubCategories())) {
            criteria = criteria.and("sub_category   ").in(filterCriteria.getSubCategories());
        }

        Sort sort = Sort.by(filterCriteria.getSortDir(), filterCriteria.getSortField());
        Pageable pageable = PageRequest.of(filterCriteria.getPage(), filterCriteria.getPageSize());
        query.with(sort);
        query.with(pageable);
        query.addCriteria(criteria);

        log.debug("fetching courses with query {}", query);
        return mongoTemplate.find(query, CourseEntity.class);
    }

    @Override
    public List<CourseEntity> fetchWithIds(List<String> courseIds) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").in(courseIds);

        query.addCriteria(criteria);
        log.debug("fetchWithIds: query {}", query);
        return mongoTemplate.find(query, CourseEntity.class);
    }

    @Override
    public UpdateResult updateCourseStatus(String courseId, CourseStatus courseStatus, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId));

        Update update = new Update();
        update.set("courseStatus", courseStatus);
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

    @Override
    public UpdateResult updateCourse(String courseId, CourseRequest courseRequest, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId));

        PricingEntity pricing = new PricingEntity();
        pricing.setAmount(courseRequest.getAmount());
        pricing.setCurrency(courseRequest.getCurrency());

        Update update = new Update();
        update.set("title", courseRequest.getTitle());
        update.set("description", courseRequest.getDescription());
        update.set("language", courseRequest.getLanguage());
        update.set("category", courseRequest.getCategory());
        update.set("sub_category", courseRequest.getSubCategory());
        update.set("thumbnail_url", courseRequest.getThumbnailUrl());
        update.set("preview_url", courseRequest.getPreviewUrl());
        update.set("pricing", pricing);
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

    @Override
    public UpdateResult addContent(String courseId, String contentId, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId));

        Update update = new Update();
        update.push("contents", new DBRef(Collections.CONTENTS, contentId));
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

    @Override
    public UpdateResult updateContent(String courseId, String contentId, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId));

        Update update = new Update();
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());

        return  mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

    @Override
    public UpdateResult deleteContent(String courseId, String contentId, UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId));

        Update update = new Update();
        update.pull("contents", new DBRef(Collections.CONTENTS, contentId));
        update.set("modified_by", new DBRef(Collections.USERS, userEntity.getId()));
        update.set("modified_date", Common.getCurrentUTCDate());
        return mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

    public UpdateResult updateCollaborationRequestStatus(String courseId, String requestId, CollaborationStatus status,
                                                  UserEntity userEntity) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(courseId)
                .and("collaboration_requests.id").is(requestId));

        Update update = new Update();
        update.set("collaboration_requests.$.status", status);
        return mongoTemplate.updateFirst(query, update, CourseEntity.class);
    }

}
