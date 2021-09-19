package com.gapfyl.services.courses;

import com.gapfyl.dto.courses.UserCourseResponse;
import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

public interface UserCourseService {

    List<UserCourseResponse> fetchUserCourses(UserEntity userEntity);

    void addUserCourse(String orderId, String paymentId, String courseId, UserEntity userEntity);

    void updateUserCourseStatus(String courseId, UserCourseStatus status, UserEntity userEntity);

    void deleteById(String id, UserEntity userEntity);
}
