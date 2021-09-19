package com.gapfyl.services.courses;

import com.gapfyl.dto.courses.CourseResponse;
import com.gapfyl.dto.courses.UserCourseResponse;
import com.gapfyl.enums.courses.UserCourseStatus;
import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.models.users.UserCourseEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.UserCourseRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

@Slf4j
@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    CourseService courseService;

    @Override
    public void addUserCourse(String orderId, String paymentId, String courseId, UserEntity userEntity) {
        log.debug("user adding {} [{}] purchased course {}", userEntity.getName(), userEntity.getId(), courseId);
        CourseEntity courseEntity = courseService.fetchCourseEntity(courseId);
        if (courseEntity == null) {
            log.error("failed to add user {} [{}] purchased course {}", userEntity.getName(), userEntity.getId(), courseId);
            return;
        }

        UserCourseEntity userCourse = new UserCourseEntity();
        userCourse.setOrderId(orderId);
        userCourse.setPaymentId(paymentId);
        userCourse.setStatus(UserCourseStatus.purchased);
        userCourse.setCourseId(courseId);
        userCourse.setAccountId(userEntity.getAccount().getId());
        userCourse.setUserId(userEntity.getId());
        userCourse.setCreatedBy(userEntity);
        userCourse.setModifiedBy(userEntity);
        userCourse.setCreatedDate(Common.getCurrentUTCDate());
        userCourse.setModifiedDate(Common.getCurrentUTCDate());
        userCourseRepository.save(userCourse);
        log.debug("user added {} [{}] purchased course {}", userEntity.getName(), userEntity.getId(), courseId);
    }

    private UserCourseResponse getUserCourseResponse(UserCourseEntity userCourse, CourseResponse courseResponse) {
        UserCourseResponse userCourseResponse = new UserCourseResponse();
        userCourseResponse.setAccountId(userCourse.getAccountId());
        userCourseResponse.setUserId(userCourse.getUserId());
        userCourseResponse.setOrderId(userCourse.getOrderId());
        userCourseResponse.setOrderId(userCourse.getOrderId());
        userCourseResponse.setPaymentId(userCourse.getPaymentId());
        userCourseResponse.setCourse(courseResponse);
        userCourseResponse.setStatus(userCourse.getStatus());
        return userCourseResponse;
    }

    @Override
    public List<UserCourseResponse> fetchUserCourses(UserEntity userEntity) {
        log.debug("fetching user {} [{}] courses", userEntity.getName(), userEntity.getId());
        List<UserCourseEntity> userCourses = userCourseRepository.fetchUserCourses(userEntity);
        log.debug("found {} user courses", userCourses.size());

        if (Common.isNullOrEmpty(userCourses)) return Collections.EMPTY_LIST;

        List<String> courseIds = userCourses.stream().map(item -> item.getCourseId()).collect(Collectors.toList());
        List<CourseResponse> courseResponses = courseService.fetchCoursesWithIds(courseIds);
        Map<String, CourseResponse> courseResponseMap = courseResponses.stream()
                .collect(Collectors.toMap(course -> course.getId(), course -> course));

        List<UserCourseResponse> userCourseResponses = userCourses.stream()
                .map(item -> getUserCourseResponse(item, courseResponseMap.get(item.getCourseId())))
                .collect(Collectors.toList());

        return userCourseResponses;
    }

    @Override
    public void updateUserCourseStatus(String courseId, UserCourseStatus status, UserEntity userEntity) {
        log.info("user {} [{}] updating course status as {}", userEntity.getName(), userEntity.getId(), status);
        userCourseRepository.updateUserCourseStatus(courseId, status, userEntity);
        log.info("user {} [{}] updated course status as {}", userEntity.getName(), userEntity.getId(), status);
    }

    @Override
    public void deleteById(String id, UserEntity userEntity) {
        log.debug("user {} [{}] deleting user course {}", userEntity.getName(), userEntity.getId(), id);
        userCourseRepository.deleteById(id);
        log.debug("user {} [{}] deleted user course {}", userEntity.getName(), userEntity.getId(), id);
    }
}
