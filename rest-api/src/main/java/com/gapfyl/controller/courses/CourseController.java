package com.gapfyl.controller.courses;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.dto.common.CollaborationRequest;
import com.gapfyl.dto.courses.CourseRequest;
import com.gapfyl.dto.courses.RateCourse;
import com.gapfyl.enums.common.CollaborationStatus;
import com.gapfyl.enums.common.CreatorAccess;
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
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.services.courses.CourseService;
import com.gapfyl.services.courses.UserCourseService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@RestController
@RequestMapping("/api/1.0/courses")
public class CourseController extends AbstractController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserCourseService userCourseService;

    @PostMapping("/create")
    ResponseEntity createCourse(@RequestBody @Valid CourseRequest courseRequest)
            throws NotFoundException, ContentNotAddedException {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(courseService.createCourse(courseRequest, userEntity));
    }

    @GetMapping("/fetch/collaborator-courses")
    ResponseEntity fetchCollaboratorCourses() throws NotFoundException {
        return ResponseEntity.ok().body(courseService.fetchCollaboratorCourses(getCurrentUser()));
    }

    @GetMapping("/fetch/creator-courses")
    ResponseEntity fetchCreatorCourses() throws NotFoundException, InActiveException {
        return ResponseEntity.ok().body(courseService.fetchCreatorCourses(getCurrentUser()));
    }

    @GetMapping("/fetch/{courseId}")
    ResponseEntity fetchCourse(@PathVariable("courseId") String courseId) throws NotFoundException, InActiveException {
        return ResponseEntity.ok().body(courseService.fetchCourse(courseId));
    }

    @GetMapping("/search")
    ResponseEntity searchCourses(@RequestParam(value = "searchText") String searchText) {
        return ResponseEntity.ok().body(courseService.searchCoursesByTitle(searchText));
    }


    @PostMapping("/fetch/filters")
    ResponseEntity coursesWithFilters(@RequestParam(value = "queryText", required = false) String queryText, @RequestBody CourseFilterCriteria courseFilterCriteria) {
        return ResponseEntity.ok().body(courseService.fetchCoursesWithFilters(queryText, courseFilterCriteria));
    }

    @PutMapping("/update/{courseId}")
    ResponseEntity updateCourse(@PathVariable("courseId") String courseId,
                                @RequestBody @Valid CourseRequest courseRequest)
            throws NotFoundException, NoPermissionException, InActiveException, UpdateFailedException,
            ContentNotFoundException, ContentNotRemovedException {

        UserEntity userEntity = getCurrentUser();
        courseService.updateCourse(courseId, courseRequest, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/update/{courseId}/status")
    ResponseEntity updateCourseStatus(@PathVariable(name = "courseId") String courseId,
                                      @RequestParam(value = "status") CourseStatus status)
            throws NotFoundException, NoPermissionException, InActiveException, UpdateFailedException {
        UserEntity userEntity = getCurrentUser();
        courseService.updateCourseStatus(courseId, status, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @DeleteMapping("/delete/{courseId}")
    ResponseEntity deleteCourse(@PathVariable("courseId") String courseId) throws Exception {
        UserEntity userEntity = getCurrentUser();
        courseService.deleteCourse(courseId, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PostMapping("/{courseId}/content")
    ResponseEntity addContent(@PathVariable("courseId") String courseId, @RequestBody ContentRequest contentRequest)
            throws ContentNotAddedException {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(courseService.addContent(courseId, contentRequest, userEntity));
    }

    @PutMapping("/{courseId}/content/{contentId}")
    ResponseEntity updateContent(@PathVariable("courseId") String courseId, @PathVariable("contentId") String contentId,
                                 @RequestBody @Valid ContentRequest contentRequest)
            throws ContentNotFoundException, ContentUpdateFailedException {
        UserEntity userEntity = getCurrentUser();
        return ResponseEntity.ok().body(courseService.updateContent(courseId, contentId, contentRequest, userEntity));
    }

    @DeleteMapping("/{courseId}/content/{contentId}")
    ResponseEntity deleteContent(@PathVariable("courseId") String courseId, @PathVariable("contentId") String contentId)
            throws ContentNotFoundException, ContentNotRemovedException {
        UserEntity userEntity = getCurrentUser();
        courseService.deleteContent(courseId, contentId, userEntity);
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/{courseId}/collaboration-invite")
    ResponseEntity collaborationRequest(@PathVariable("courseId") String courseId,
                                        @RequestBody @Valid CollaborationRequest collaborationRequest)
            throws NotFoundException, InActiveException {
        return ResponseEntity.ok().body(courseService.collaborationRequest(courseId, collaborationRequest, getCurrentUser()));
    }

    @PutMapping("/{courseId}/collaboration-accept/{requestId}")
    ResponseEntity acceptCollaborationRequest(@PathVariable("courseId")String courseId,
                                              @PathVariable("requestId")String requestId,
                                              @RequestParam("status") CollaborationStatus status)
            throws NotFoundException, InvalidRequestException {

        courseService.acceptCollaborationRequest(courseId, requestId, status,getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @PutMapping("/{courseId}/update-accesses/{creatorId}")
    ResponseEntity updateCreatorAccesses(@PathVariable("courseId")String courseId,
                                         @PathVariable("creatorId")String creatorId,
                                         @RequestParam("accesses") List<CreatorAccess> accesses)
            throws NotFoundException {

        courseService.updateCreatorAccesses(courseId,creatorId,accesses,getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success",true).build());
    }

    @PutMapping("/{courseId}/remove-collaborator/{collaboratorId}")
    ResponseEntity removeCollaboratorByCreator(@PathVariable("courseId") String courseId,
                                               @PathVariable("collaboratorId") String collaboratorId) throws NotFoundException {

        courseService.removeCollaborator(courseId, collaboratorId, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success",true).build());
    }

    @PutMapping("/{courseId}/rate-course")
    ResponseEntity rateCourse(@PathVariable("courseId") String courseId, @RequestBody RateCourse rateCourse)
            throws NotFoundException, InActiveException {
        courseService.rateCourse(courseId, rateCourse, getCurrentUser());
        return ResponseEntity.ok().body(ImmutableMap.builder().put("success", true).build());
    }

    @GetMapping("/fetch/user-courses")
    ResponseEntity userCourses() {
        return ResponseEntity.ok().body(userCourseService.fetchUserCourses(getCurrentUser()));
    }
}
