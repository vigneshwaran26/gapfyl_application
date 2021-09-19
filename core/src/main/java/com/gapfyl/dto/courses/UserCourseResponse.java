package com.gapfyl.dto.courses;

import com.gapfyl.dto.users.UserResponse;
import com.gapfyl.enums.courses.UserCourseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 01/08/21
 **/

@Getter
@Setter
public class UserCourseResponse {

    private String id;

    private String orderId;

    private String paymentId;

    private String accountId;

    private String userId;

    private CourseResponse course;

    private UserCourseStatus status;

}
