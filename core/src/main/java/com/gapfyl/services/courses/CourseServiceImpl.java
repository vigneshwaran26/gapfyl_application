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
import com.gapfyl.enums.common.Currency;
import com.gapfyl.enums.courses.CourseStatus;
import com.gapfyl.enums.products.ProductType;
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
import com.gapfyl.models.common.CollaborationRequestEntity;
import com.gapfyl.models.common.PricingEntity;
import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.models.courses.CourseEntity;
import com.gapfyl.models.users.creators.CreatorStatus;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.products.ProductEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.CourseRepository;
import com.gapfyl.services.contents.ContentService;
import com.gapfyl.services.users.creators.CreatorService;
import com.gapfyl.services.email.EmailService;
import com.gapfyl.services.products.ProductService;
import com.gapfyl.services.users.UserService;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 18/04/21
 **/

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    ContentService contentService;

    @Autowired
    CreatorService creatorService;

    @Autowired
    ProductService productService;

    @Value("${client.origin:no-client-origin}")
    private String clientOrigin;

    @Override
    public CourseDetailResponse entityToDetailResponse(CourseEntity courseEntity) {
        CourseDetailResponse courseDetailResponse = new CourseDetailResponse();
        courseDetailResponse.setId(courseEntity.getId());
        courseDetailResponse.setTitle(courseEntity.getTitle());
        courseDetailResponse.setDescription(courseEntity.getDescription());
        courseDetailResponse.setLanguage(courseEntity.getLanguage());
        courseDetailResponse.setCategory(courseEntity.getCategory());
        courseDetailResponse.setSubCategory(courseEntity.getSubCategory());
        courseDetailResponse.setTags(courseEntity.getTags());
        courseDetailResponse.setStatus(courseEntity.getStatus());
        courseDetailResponse.setThumbnailUrl(courseEntity.getThumbnailUrl());
        courseDetailResponse.setPreviewUrl(courseEntity.getPreviewUrl());

        if (courseEntity.getPricing() != null) {
            Currency currency = Currency.valueOf(courseEntity.getPricing().getCurrency().name());
            courseDetailResponse.setCurrencyCode(currency.getCode());
            courseDetailResponse.setCurrencySymbol(currency.getSymbol());
            courseDetailResponse.setAmount(courseEntity.getPricing().getAmount());
        }

        courseDetailResponse.setActive(courseEntity.isActive());
        if (!Common.isNullOrEmpty(courseEntity.getContents())) {
            List<ContentResponse> contentResponses = new ArrayList<>();
            for (ContentEntity content: courseEntity.getContents()) {
                contentResponses.add(contentService.entityToDto(content));
            }
            courseDetailResponse.setContents(contentResponses);
        }

        return courseDetailResponse;
    }


    public CourseResponse entityToResponse(CourseEntity courseEntity) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(courseEntity.getId());
        courseResponse.setTitle(courseEntity.getTitle());
        courseResponse.setLanguage(courseEntity.getLanguage());
        courseResponse.setDescription(courseEntity.getDescription());
        courseResponse.setThumbnailUrl(courseEntity.getThumbnailUrl());
        courseResponse.setPreviewUrl(courseEntity.getPreviewUrl());

        if (courseEntity.getPricing() != null) {
            Currency currency = Currency.valueOf(courseEntity.getPricing().getCurrency().name());
            PricingEntity pricingEntity = courseEntity.getPricing();
            courseResponse.setAmount(pricingEntity.getAmount());
            courseResponse.setCurrencyCode(currency.getCode());
            courseResponse.setCurrencySymbol(currency.getSymbol());
        }

        return courseResponse;
    }

    @Override
    public CourseDetailResponse createCourse(CourseRequest courseRequest, UserEntity userEntity)
            throws NotFoundException, ContentNotAddedException {

        log.info("user {} [{}] creating course", userEntity.getName(), userEntity.getId());
        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null) {
            log.error("creator {} not found", userEntity.getId());
            throw new NotFoundException("creator.not_found");
        }

        Map<String, List<CreatorAccess>> access = new HashMap<>();
        access.put(creator.getId(), Arrays.asList(CreatorAccess.full_access));

        PricingEntity pricing = new PricingEntity();
        pricing.setAmount(courseRequest.getAmount());
        pricing.setCurrency(courseRequest.getCurrency());

        // FIXME: tags manipulation for test version
        List<String> tags = courseRequest.getTags();
        if (Common.isNullOrEmpty(tags)) {
          tags = new ArrayList<>();
        }

        tags.addAll(Arrays.asList("new", "popular"));

        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setTitle(courseRequest.getTitle());
        courseEntity.setDescription(courseRequest.getDescription());
        courseEntity.setLanguage(courseRequest.getLanguage());
        courseEntity.setCategory(courseRequest.getCategory());
        courseEntity.setSubCategory(courseRequest.getSubCategory());
        courseEntity.setStatus(CourseStatus.created);
        courseEntity.setThumbnailUrl(courseRequest.getThumbnailUrl());
        courseEntity.setPreviewUrl(courseRequest.getPreviewUrl());
        courseEntity.setTags(courseRequest.getTags());
        courseEntity.setActive(true);
        courseEntity.setPricing(pricing);
        courseEntity.setCreators(Arrays.asList(userEntity.getId()));
        courseEntity.setCreatorAccesses(access);
        courseEntity.setCreatedDate(Common.getCurrentUTCDate());
        courseEntity.setModifiedDate(Common.getCurrentUTCDate());

        courseEntity.setCreatedBy(userEntity);
        courseEntity.setModifiedBy(userEntity);
        courseEntity = courseRepository.save(courseEntity);

        if (!Common.isNullOrEmpty(courseRequest.getContents())) {
            for (ContentRequest content: courseRequest.getContents()) {
                addContent(courseEntity.getId(), content, userEntity);
            }
        }

        log.info("user {} [{}] created course {} [{}]", userEntity.getName(), userEntity.getId(),
                courseEntity.getTitle(), courseEntity.getId());

        /**
         * adding course as a product to user to purchase
         * */
        addCourseAsProduct(courseEntity, userEntity);

        return entityToDetailResponse(courseEntity);
    }

    @Async("gapFylTaskExecutor")
    public void addCourseAsProduct(CourseEntity courseEntity, UserEntity userEntity) {
        log.info("adding course {} [{}] as a product", courseEntity.getTitle(), courseEntity.getId());
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductId(courseEntity.getId());
        productEntity.setName(courseEntity.getTitle());
        productEntity.setDescription(courseEntity.getDescription());
        productEntity.setCategory(courseEntity.getCategory());
        productEntity.setSubCategory(courseEntity.getSubCategory());
        productEntity.setType(ProductType.course);
        productEntity.setPricing(courseEntity.getPricing());
        productEntity.setActive(true);
        productService.addProduct(productEntity, userEntity);
    }

    private void updateCourseAsProduct(CourseEntity courseEntity, ProductEntity productEntity, UserEntity userEntity) {
        productEntity.setName(courseEntity.getTitle());
        productEntity.setDescription(courseEntity.getDescription());
        productEntity.setCategory(courseEntity.getCategory());
        productEntity.setSubCategory(courseEntity.getSubCategory());
        productEntity.setPricing(courseEntity.getPricing());
        productEntity.setActive(courseEntity.isActive());
        productService.updateProduct(productEntity, userEntity);
    }

    @Override
    public void saveOrUpdateCoursesAsProduct(UserEntity userEntity) {
        List<CourseEntity> courses = courseRepository.findAll();
        courses.forEach(item -> {
            ProductEntity existingProduct = productService.fetchProductEntity(item.getId());
            if (existingProduct == null) addCourseAsProduct(item, userEntity);
            else updateCourseAsProduct(item, existingProduct, userEntity);
        });
    }

    @Override
    public CourseEntity fetchCourseEntity(String courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    @Override
    public CourseDetailResponse fetchCourse(String courseId) throws NotFoundException, InActiveException    {
        CourseEntity courseEntity = courseRepository.findById(courseId).orElse(null);
        if (courseEntity == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[] { courseId });
        }

        if (!courseEntity.isActive()) {
            log.error("course {} [{}] is not active in status", courseEntity.getTitle(), courseEntity.getId());
            throw new InActiveException("course.in_active", new Object[] { courseEntity.getTitle() });
        }

        return entityToDetailResponse(courseEntity);
    }

    public  List<CourseEntity> fetchAllCourses(){
        List<CourseEntity> courseEntities = courseRepository.findAll();
        return courseEntities;
    }

    @Override
    public List<CourseResponse> fetchCollaboratorCourses(UserEntity userEntity) throws NotFoundException {
        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null) {
            log.error("creator {} not found", userEntity.getId());
            throw new NotFoundException(userEntity.getId());
        }

        List<CourseEntity> courses = courseRepository.fetchCollaboratorCourses(userEntity.getId());
        log.info("found {} courses", courses.size());
        return courses.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> fetchCreatorCourses(UserEntity userEntity) throws NotFoundException, InActiveException {

        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null){
            log.error("user creator profile {} not found", userEntity.getId());
            throw new NotFoundException(userEntity.getId());
        }

        if (creator.getStatus().equals(CreatorStatus.deactivated)){
            log.error("creator {} have no permission",creator.getId());
            throw new InActiveException("creator.not_active", new Object[]{ creator.getId() });
        }

        List<CourseEntity> courses = courseRepository.fetchCreatorCourses(userEntity.getId());
        log.info("found {} courses", courses.size());

        return courses.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> fetchCoursesWithFilters(String queryText, CourseFilterCriteria filterCriteria) {
        log.info("fetching courses with filterCriteria {}", filterCriteria);
        List<CourseEntity> courses = courseRepository.fetchWithFilters(queryText, filterCriteria);

        log.debug("found {} courses", courses.size());
        return courses.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> searchCoursesByTitle(String searchText) {
        List<CourseEntity> courses = courseRepository.searchByTitle(searchText);
        if (Common.isNullOrEmpty(courses)) return Collections.EMPTY_LIST;

        return courses.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> fetchCoursesWithIds(List<String> courseIds) {
        log.info("fetching courses with ids {}", courseIds.toString());
        List<CourseEntity> courses = courseRepository.fetchWithIds(courseIds);

        log.info("found {} courses", courses.size());
        return courses.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }

    @Override
    public CourseDetailResponse updateCourse(String courseId, CourseRequest courseRequest, UserEntity userEntity)
            throws NotFoundException, InActiveException, NoPermissionException, UpdateFailedException {

        log.info("user {} [{}] updating course {}", userEntity.getName(), userEntity.getId(), courseId);
        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[]{ courseId });
        }

        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null) {
            log.error("creator {} not found", userEntity.getEmail());
            throw new NotFoundException("creator.not_found", new Object[] { userEntity.getEmail() });
        }

        if (creator.getStatus().equals(CreatorStatus.deactivated)) {
            log.error("creator {} have no permission",course.getId());
            throw new InActiveException("creator.not_active", new Object[] { userEntity.getEmail() });
        }

        Map<String, List<CreatorAccess>> accesses = course.getCreatorAccesses();
        if (Common.isNullOrEmpty(accesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        List<CreatorAccess> creatorAccesses = accesses.get(creator.getId());
        if (Common.isNullOrEmpty(creatorAccesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        if (!creatorAccesses.contains(CreatorAccess.write) && !creatorAccesses.contains(CreatorAccess.full_access)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        log.debug("updating course contents");
        updateCourseContents(course, courseRequest, userEntity);

        UpdateResult updateResult = courseRepository.updateCourse(courseId, courseRequest, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("course {} failed to update", courseId);
            throw new UpdateFailedException("course.update.failed", new Object[]{ course.getTitle() });
        }

        CourseEntity updated = courseRepository.findById(courseId).orElse(null);
        log.info("user {} [{}] updated course {}", userEntity.getName(), userEntity.getId(), courseId);

        return entityToDetailResponse(updated);
    }

    private void updateCourseContents(CourseEntity existingCourse, CourseRequest courseRequest, UserEntity userEntity) {

        List<ContentEntity> existingContents = existingCourse.getContents();
        if (Common.isNullOrEmpty(existingContents)) {
            log.debug("existing course {} has no contents", existingCourse.getId());
        }

        List<ContentRequest> updateContents = courseRequest.getContents();
        if (Common.isNullOrEmpty(updateContents)) {
            log.warn("update course request with no contents");
            log.warn("delete existing contents if exists");

            if (!Common.isNullOrEmpty(existingContents)) {
                existingContents.forEach(deleteContent -> {
                    log.debug("deleting content {}, from course {}", existingCourse.getId(), deleteContent.getId());
                    try {
                        deleteContent(existingCourse.getId(), deleteContent.getId(), userEntity);
                    } catch (ContentNotFoundException e) {
                        log.error("course update request {}, failed to delete existing content {}",
                                existingCourse.getId(), deleteContent.getId());
                        e.printStackTrace();
                    } catch (ContentNotRemovedException e) {
                        log.error("course update request {}, failed to delete existing content {}",
                                existingCourse.getId(), deleteContent.getId());
                        e.printStackTrace();
                    }
                });

            }
        }
        else if (Common.isNullOrEmpty(existingContents)) {
            log.debug("adding {} contents to course {}", updateContents.size(), existingCourse.getId());
            updateContents.forEach(content -> {
                try {
                    addContent(existingCourse.getId(), content, userEntity);
                } catch (ContentNotAddedException e) {
                    log.error("failed to add content {}", e);
                    e.printStackTrace();
                }
            });

        } else {
                List<ContentRequest> newUpdateContents = updateContents.stream()
                        .filter(item -> item.getId() == null).collect(Collectors.toList());

                log.debug("found {} new contents to update course {}", newUpdateContents.size(), existingCourse.getId());

                List<ContentRequest> existingUpdateContents = updateContents.stream()
                        .filter(item -> item.getId() != null).collect(Collectors.toList());

                log.debug("found {} existing contents to update course {}", existingUpdateContents.size(),
                        existingCourse.getId());

                if (!Common.isNullOrEmpty(newUpdateContents)) {
                    log.debug("adding new contents to course {}", existingCourse.getId());
                    newUpdateContents.forEach(content -> {
                        try {
                            addContent(existingCourse.getId(), content, userEntity);
                        } catch (ContentNotAddedException e) {
                            log.error("failed to add content {}", e);
                            e.printStackTrace();
                        }
                    });
                }

                List<String> existingContentsIds = existingContents.stream()
                        .map(content -> content.getId()).collect(Collectors.toList());

                List<String> existingUpdateContentsIds = existingUpdateContents.stream()
                        .map(content -> content.getId()).collect(Collectors.toList());

                List<String> existingMissingContentsIds = existingContentsIds.stream()
                        .filter(id -> !existingUpdateContentsIds.contains(id)).collect(Collectors.toList());

                log.debug("found {} contents missing from update course request {}",
                        existingMissingContentsIds.size(), existingCourse.getId());

                if (!Common.isNullOrEmpty(existingMissingContentsIds)) {
                    log.debug("deleting {} missing contents from course {}",
                            existingMissingContentsIds.size(), existingCourse.getId());

                    existingMissingContentsIds.forEach(missingContentId -> {
                        try {
                            deleteContent(existingCourse.getId(), missingContentId, userEntity);
                        } catch (ContentNotFoundException e) {
                            log.error("course update request {}, failed to delete content {}",
                                    existingCourse.getId(), missingContentId);
                            e.printStackTrace();
                        } catch (ContentNotRemovedException e) {
                            log.error("course update request {}, failed to delete content {}",
                                    existingCourse.getId(), missingContentId);
                            e.printStackTrace();
                        }
                    });
                }

                log.debug("updating {} contents in course update request {}",
                        existingUpdateContents.size(), existingCourse.getId());

                existingUpdateContents.forEach(updateContent -> {
                    try {
                        log.debug("course update request {}, updating content {}", existingCourse.getId(), updateContent.getId());
                        updateContent(existingCourse.getId(), updateContent.getId(), updateContent, userEntity);
                    } catch (ContentNotFoundException e) {
                        log.error("course update request {}, failed to update content {}", existingCourse.getId(), updateContent.getId());
                        e.printStackTrace();
                    } catch (ContentUpdateFailedException e) {
                        log.error("course update request {}, failed to update content {}", existingCourse.getId(), updateContent.getId());
                        e.printStackTrace();
                    }
                });
            }

    }

    @Override
    public CourseDetailResponse updateCourseStatus(String courseId, CourseStatus courseStatus, UserEntity userEntity)
            throws NotFoundException, InActiveException, NoPermissionException, UpdateFailedException {

        log.info("user {} [{}] updating course {} courseStatus as {}", userEntity.getName(), userEntity.getId(),
                courseId, courseStatus);

        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException(courseId);
        }

        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null) {
            log.error("creator {} not found", userEntity.getEmail());
            throw new NotFoundException("creator.not_found", new Object[] { userEntity.getEmail() });
        }

        if (creator.getStatus().equals(CreatorStatus.deactivated)) {
            log.error("creator {} have no permission",course.getId());
            throw new InActiveException("creator.not_active", new Object[] { userEntity.getEmail() });
        }

        Map<String, List<CreatorAccess>> accesses = course.getCreatorAccesses();
        if (Common.isNullOrEmpty(accesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        List<CreatorAccess> creatorAccesses = accesses.get(creator.getId());
        if (Common.isNullOrEmpty(creatorAccesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        if (creatorAccesses.contains(CreatorAccess.write) | creatorAccesses.contains(CreatorAccess.full_access)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        UpdateResult updateResult = courseRepository.updateCourseStatus(courseId, courseStatus, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("course {} failed to update", courseId);
            throw new UpdateFailedException(courseId);
        }

        CourseEntity updated = courseRepository.findById(courseId).orElse(null);
        log.debug("user {} [{}] updated course {} courseStatus as {}", userEntity.getName(), userEntity.getId(), courseId, courseStatus);

        return entityToDetailResponse(updated);
    }

    @Override
    public void deleteCourse(String courseId, UserEntity userEntity)
            throws NotFoundException, InActiveException, NoPermissionException, UpdateFailedException {
        log.info("user {} [{}] deleting course {}", userEntity.getName(), userEntity.getId(), courseId);
        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[]{ courseId });
        }

        CreatorEntity creator = creatorService.fetchByUserId(userEntity.getId());
        if (creator == null) {
            log.error("creator {} not found", userEntity.getEmail());
            throw new NotFoundException("creator.not_found", new Object[] { userEntity.getEmail() });
        }

        if (creator.getStatus().equals(CreatorStatus.deactivated)) {
            log.error("creator {} have no permission",course.getId());
            throw new InActiveException("creator.not_active", new Object[] { userEntity.getEmail() });
        }

        Map<String, List<CreatorAccess>> accesses = course.getCreatorAccesses();
        if (Common.isNullOrEmpty(accesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        List<CreatorAccess> creatorAccesses = accesses.get(creator.getId());
        if (Common.isNullOrEmpty(creatorAccesses)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        if (!creatorAccesses.contains(CreatorAccess.write) && !creatorAccesses.contains(CreatorAccess.full_access)) {
            log.error("creator doesn't have permission to delete course {}", creator.getId(), courseId);
            throw new NoPermissionException("creator.no_permission", new Object[] { userEntity.getName() });
        }

        courseRepository.deleteById(courseId);
        log.debug("user {} [{}] deleted course {}", userEntity.getName(), userEntity.getId(), courseId);
    }

    @Override
    public ContentResponse addContent(String courseId, ContentRequest contentRequest, UserEntity userEntity)
            throws ContentNotAddedException {

        ContentResponse contentResponse = contentService.createContent(contentRequest, userEntity);
        UpdateResult updateResult = courseRepository.addContent(courseId, contentResponse.getId(), userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("content {} is not added to course {}", contentResponse.getId(), courseId);
            throw new ContentNotAddedException(contentResponse.getTitle());
        }

        log.info("user {} [{}] added content to course {}", userEntity.getName(), userEntity.getId(), courseId);
        return contentResponse;
    }

    @Override
    public ContentResponse updateContent(String courseId, String contentId, ContentRequest contentRequest, UserEntity userEntity)
            throws ContentNotFoundException, ContentUpdateFailedException {
        log.info("user {} [{}] updating course {} content {}", userEntity.getName(), userEntity.getId(), courseId, contentId);
        ContentResponse contentResponse = contentService.updateContent(contentId, contentRequest, userEntity);
        UpdateResult updateResult = courseRepository.updateContent(courseId, contentId, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("content {} failed to update", contentId);
            throw new ContentUpdateFailedException(contentId);
        }
        log.info("user {} [{}] updated course {} content {}", userEntity.getName(), userEntity.getId(), courseId, contentId);
        return contentResponse;
    }

    @Override
    public void deleteContent(String courseId, String contentId, UserEntity userEntity)
            throws ContentNotFoundException, ContentNotRemovedException {
        log.info("user {} [{}] deleting course {} content {}", userEntity.getName(), userEntity.getId(), courseId, contentId);
        contentService.deleteContent(contentId, userEntity);
        UpdateResult updateResult = courseRepository.deleteContent(courseId, contentId, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("failed to remove content {} associated with course {}", contentId, courseId);
            throw new ContentNotRemovedException(contentId);
        }
        log.info("user {} [{}] deleted course {} content {}", userEntity.getName(), userEntity.getId(), courseId, contentId);
    }

    @Override
    public CollaborationResponse collaborationRequest(String courseId, CollaborationRequest collaborationRequest, UserEntity userEntity)
            throws NotFoundException, InActiveException {

        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[]{ courseId });
        }

        CreatorEntity requester = creatorService.fetchById(collaborationRequest.getRequesterId());
        if (requester == null) {
            log.error("collaboration request failed, requester {} not found", collaborationRequest.getRequesterId());
            throw new NotFoundException("collaboration.requester.not_found");
        }

        if (requester.getStatus().equals(CreatorStatus.deactivated)) {
            log.error("collaboration request failed, requester {} deactivated", collaborationRequest.getRequesterId());
            throw new InActiveException("collaboration.requester.not_active");
        }

        CreatorEntity collaborator = creatorService.fetchById(collaborationRequest.getCollaboratorId());
        if (collaborator == null) {
            log.error("collaboration request failed, collaborator {} not found", collaborationRequest.getRequesterId());
            throw new NotFoundException("collaboration.collaborator.not_found");
        }

        if (collaborator.getStatus().equals(CreatorStatus.deactivated)) {
            log.error("collaboration request failed, collaborator {} not active", collaborationRequest.getRequesterId());
            throw new InActiveException("collaboration.collaborator.not_active");
        }

        CollaborationRequestEntity collaborationRequestEntity = new CollaborationRequestEntity();
        collaborationRequestEntity.setId(UUID.randomUUID().toString());
        collaborationRequestEntity.setRequesterId(collaborationRequest.getRequesterId());
        collaborationRequestEntity.setCollaboratorId(collaborationRequestEntity.getCollaboratorId());
        collaborationRequestEntity.setAccesses(collaborationRequest.getAccesses());
        collaborationRequestEntity.setStatus(CollaborationStatus.invited);

        List<CollaborationRequestEntity> collaborationRequests = course.getCollaborationRequests();
        if (Common.isNullOrEmpty(collaborationRequests)) collaborationRequests = new ArrayList<>();
        collaborationRequests.add(collaborationRequestEntity);
        course.setCollaborationRequests(collaborationRequests);

        course.setModifiedBy(userEntity);
        course.setModifiedDate(Common.getCurrentUTCDate());
        courseRepository.save(course);

        // FIXME: send mail to invite
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        return collaborationResponse;
    }

    @Override
    public void acceptCollaborationRequest(String courseId, String requestId, CollaborationStatus status, UserEntity userEntity)
            throws NotFoundException, InvalidRequestException {
        log.info("requesting creator {} to accept request", requestId);
        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[]{ courseId });
        }

        List<CollaborationRequestEntity> collaborations = course.getCollaborationRequests();
        if (Common.isNullOrEmpty(collaborations)) {
            log.error("collaboration request {} not found", requestId);
            throw new NotFoundException("collaboration.request.not_found");
        }

        CollaborationRequestEntity collaborationRequest = collaborations.stream()
                .filter(item -> item.getId().equals(requestId)).findAny().orElse(null);

        if (collaborationRequest == null) {
            log.error("collaboration request {} not found", requestId);
            throw new NotFoundException("collaboration.request.not_found");
        }

        if (!collaborationRequest.getStatus().equals(CollaborationStatus.invited)) {
            log.error("Invalid collaboration request {}", requestId);
            throw new InvalidRequestException("collaboration.request.invalid");
        }

        log.info("updating the status as per collaborator request {}", requestId);
        collaborations.forEach(request -> {
            if (request.getId().equals(requestId)) request.setStatus(status);
        });

        course.setCollaborationRequests(collaborations);

        if (status.equals(CollaborationStatus.accepted)) {
            List<String> collaborators = course.getCollaborators();
            if (Common.isNullOrEmpty(collaborators)) collaborators = new ArrayList<>();

            CreatorEntity collaborator = creatorService.fetchById(collaborationRequest.getCollaboratorId());
            collaborators.add(collaborator.getUserId());
            course.setCollaborators(collaborators);

            Map<String, List<CreatorAccess>> accesses = course.getCreatorAccesses();
            if (Common.isNullOrEmpty(accesses)) accesses = new HashMap<>();

            List<CreatorAccess> collaboratorAccesses = collaborationRequest.getAccesses();
            accesses.put(collaborationRequest.getCollaboratorId(), collaboratorAccesses);
            course.setCreatorAccesses(accesses);
        }

        course.setModifiedBy(userEntity);
        course.setModifiedDate(Common.getCurrentUTCDate());
        courseRepository.save(course);
        log.info("collaborator accepted the request{}", requestId);
    }

    @Override
    public void updateCreatorAccesses(String courseId, String creatorId, List<CreatorAccess> accesses,
                                      UserEntity userEntity) throws NotFoundException {

        log.info("user {} [{}] updating creator {} access {}", userEntity.getName(), userEntity.getId(), creatorId, accesses);
        CourseEntity courseEntity = courseRepository.findById(courseId).orElse(null);
        if (courseEntity == null) {
            log.error("course {} not found ", courseId);
            throw new NotFoundException("course.not_found", new Object[] { courseId });
        }

        String creator = courseEntity.getCreators()
                .stream().filter(item -> item.equals(creatorId)).findAny().orElse(null);

        if (creator == null) {
            creator = courseEntity.getCollaborators().stream()
                    .filter(item -> item.equals(creatorId)).findAny().orElse(null);
        }

        if (creator == null) {
            log.error("creator {} not found", creatorId);
            throw new NotFoundException("creator.not_found");
        }

        Map<String, List<CreatorAccess>> creatorAccesses = courseEntity.getCreatorAccesses();
        creatorAccesses.put(creatorId, accesses);

        courseEntity.setCreatorAccesses(creatorAccesses);
        courseEntity.setModifiedBy(userEntity);
        courseEntity.setModifiedDate(Common.getCurrentUTCDate());
        courseRepository.save(courseEntity);
        log.info("creator {} updated accesses", creatorId, accesses);
    }

    @Override
    public void removeCollaborator(String courseId, String collaboratorId, UserEntity userEntity)
            throws NotFoundException {

        log.info("");
        CourseEntity course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[] { courseId });
        }

        List<String> collaborators = course.getCollaborators();
        String collaborator = collaborators.stream()
                .filter(item -> item.equals(collaboratorId)).findAny().orElse(null);

        if (collaborator == null) {
            log.error("collaborator {} not found", collaboratorId);
            throw new NotFoundException("collaboration.collaborator.not_found");
        }

        collaborators.remove(collaborator);
        course.setCollaborators(collaborators);
        courseRepository.save(course);

        // FIXME: send notification mail to collaborator

    }

    @Override
    public void rateCourse(String courseId, RateCourse rateCourse, UserEntity userEntity)
            throws NotFoundException, InActiveException {

        log.info("user {} [{}] rating course {} ", userEntity.getName(), userEntity.getId(), courseId);
        CourseEntity courseEntity = fetchCourseEntity(courseId);
        if (courseEntity == null) {
            log.error("course {} not found", courseId);
            throw new NotFoundException("course.not_found", new Object[]{ courseId });
        }

        if (!courseEntity.isActive()) {
            log.error("course {} not active", courseId);
            throw new InActiveException("course.in_active");

        }

        Double avgRatings = courseEntity.getAvgRatings();
        int numOfRatings = courseEntity.getNumOfRatings();
        log.debug("course {} avg ratings", courseId, avgRatings);

        Double newAvgRatings = ((avgRatings * numOfRatings) + rateCourse.getRating()) / (numOfRatings + 1);
        log.debug("course {} calculated avg ratings {}", courseId, newAvgRatings);

        courseEntity.setAvgRatings(newAvgRatings);
        courseEntity.setNumOfRatings(numOfRatings + 1);
        courseEntity.setModifiedBy(userEntity);
        courseEntity.setModifiedDate(Common.getCurrentUTCDate());
        courseRepository.save(courseEntity);
        log.info("user {} [{}] rated course {} ", userEntity.getName(), userEntity.getId(), courseId);
    }
}

