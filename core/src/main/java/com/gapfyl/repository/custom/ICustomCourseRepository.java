package com.gapfyl.repository.custom;

import com.gapfyl.dto.courses.CourseRequest;
import com.gapfyl.enums.common.CollaborationStatus;
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.filter.courses.CourseFilterCriteria;
import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

public interface ICustomCourseRepository {

    List<CourseEntity> fetchCreatorCourses(String creatorId);

    List<CourseEntity> fetchCollaboratorCourses(String collaboratorId);

    List<CourseEntity> fetchWithFilters(String queryText, CourseFilterCriteria courseFilterCriteria);

    List<CourseEntity> fetchWithIds(List<String> courseIds);

    UpdateResult updateCourse(String courseId, CourseRequest courseRequest, UserEntity userEntity);

    UpdateResult updateCourseStatus(String courseId, CourseStatus courseStatus, UserEntity userEntity);

    UpdateResult addContent(String courseId, String contentId, UserEntity userEntity);

    UpdateResult updateContent(String courseId, String contentId, UserEntity userEntity);

    UpdateResult deleteContent(String courseId, String contentId, UserEntity userEntity);

    UpdateResult updateCollaborationRequestStatus(String courseId, String emailId, CollaborationStatus status,
                                                  UserEntity userEntity);

}
