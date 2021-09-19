package com.gapfyl.repository.custom;

import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.models.users.UserCourseEntity;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

/**
 * @author vignesh
 * Created on 01/08/21
 **/

public interface ICustomUserCourseRepository {

    List<UserCourseEntity> fetchUserCourses(UserEntity userEntity);

    UpdateResult updateUserCourseStatus(String courseId, UserCourseStatus status, UserEntity userEntity);

}
