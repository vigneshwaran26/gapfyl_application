package com.gapfyl.services.courses;

import com.gapfyl.dto.common.CollaborationResponse;
import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.dto.common.CollaborationRequest;
import com.gapfyl.dto.courses.CourseDetailResponse;
import com.gapfyl.dto.courses.CourseRequest;
import com.gapfyl.dto.courses.CourseResponse;
import com.gapfyl.dto.courses.RateCourse;
import com.gapfyl.enums.common.CollaborationStatus;
import com.gapfyl.enums.common.CreatorAccess;
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.exceptions.common.InActiveException;
import com.gapfyl.exceptions.common.InvalidRequestException;
import com.gapfyl.exceptions.common.NoPermissionException;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.exceptions.contents.ContentNotFoundException;
import com.gapfyl.exceptions.contents.ContentNotAddedException;
import com.gapfyl.exceptions.contents.ContentNotRemovedException;
import com.gapfyl.exceptions.contents.ContentUpdateFailedException;
import com.gapfyl.exceptions.common.UpdateFailedException;
import com.gapfyl.filter.courses.CourseFilterCriteria;
import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

public interface CourseService {

    CourseDetailResponse entityToDetailResponse(CourseEntity courseEntity);

    CourseDetailResponse createCourse(CourseRequest courseDTO, UserEntity userEntity) throws NotFoundException, ContentNotAddedException;

    public void addCourseAsProduct(CourseEntity courseEntity, UserEntity userEntity);

    List<CourseResponse> fetchCollaboratorCourses(UserEntity userEntity) throws NotFoundException;

    List<CourseResponse> fetchCreatorCourses(UserEntity userEntity) throws NotFoundException, InActiveException;

    CourseEntity fetchCourseEntity(String courseId);

    CourseDetailResponse fetchCourse(String courseId) throws NotFoundException, InActiveException;

    List<CourseEntity> fetchAllCourses();

    List<CourseResponse> fetchCoursesWithFilters(String queryText, CourseFilterCriteria courseFilterCriteria);

    List<CourseResponse> fetchCoursesWithIds(List<String> courseIds);

    List<CourseResponse> searchCoursesByTitle(String searchText);

    CourseDetailResponse updateCourse(String courseId, CourseRequest courseRequest, UserEntity userEntity)
            throws NotFoundException, NoPermissionException, InActiveException, UpdateFailedException, ContentNotFoundException, ContentNotRemovedException;

    CourseDetailResponse updateCourseStatus(String courseId, CourseStatus courseStatus, UserEntity userEntity)
            throws NotFoundException, NoPermissionException, InActiveException, UpdateFailedException;

    void deleteCourse(String courseId, UserEntity userEntity) throws Exception;

    void saveOrUpdateCoursesAsProduct(UserEntity userEntity);

    ContentResponse addContent(String courseId, ContentRequest contentRequest, UserEntity userEntity)
            throws ContentNotAddedException;

    ContentResponse updateContent(String courseId, String contentId, ContentRequest contentRequest,
                                  UserEntity userEntity) throws ContentNotFoundException, ContentUpdateFailedException;

    void deleteContent(String courseId, String contentId, UserEntity userEntity)
            throws ContentNotFoundException, ContentNotRemovedException;

    CollaborationResponse collaborationRequest(String courseId, CollaborationRequest collaborationRequest, UserEntity userEntity)
            throws NotFoundException, InActiveException;

     void acceptCollaborationRequest(String courseId, String requestId, CollaborationStatus status, UserEntity userEntity)
             throws NotFoundException, InvalidRequestException;

     void updateCreatorAccesses(String courseId, String creatorId, List<CreatorAccess> accesses, UserEntity userEntity)
             throws NotFoundException;

    void removeCollaborator(String courseId, String creatorId, UserEntity userEntity)
            throws NotFoundException;

    void rateCourse(String courseId, RateCourse rateCourse, UserEntity userEntity)
            throws NotFoundException, InActiveException;

}
