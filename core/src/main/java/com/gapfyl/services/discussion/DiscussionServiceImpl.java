package com.gapfyl.services.discussion;


import com.gapfyl.dto.discussions.DiscussionResponse;
import com.gapfyl.dto.discussions.DiscussionRequest;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.discussions.DiscussionNotFoundException;
import com.gapfyl.exceptions.discussions.DiscussionUpdateFailedException;
import com.gapfyl.models.common.CategoryEntity;
import com.gapfyl.models.discussions.DiscussionEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.DiscussionRepository;
import com.gapfyl.services.users.UserService;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class DiscussionServiceImpl implements  DiscussionService {

    @Autowired
    UserService userService;

    @Autowired
    DiscussionRepository discussionRepository;

    private DiscussionResponse entityToResponse(DiscussionEntity discussionEntity) {
        DiscussionResponse discussionResponse = new DiscussionResponse();
        discussionResponse.setId(discussionEntity.getId());
        discussionResponse.setTitle(discussionEntity.getTitle());
        discussionResponse.setDescription(discussionEntity.getDescription());

        if (!Common.isNull(discussionEntity.getCategory())) {
            discussionResponse.setCategoryType(discussionEntity.getCategory().getType());
            discussionResponse.setCategoryId(discussionEntity.getCategory().getId());
        }

        discussionResponse.setTags(discussionEntity.getTags());
        if (!Common.isNullOrEmpty(discussionEntity.getReplies())) {
            List<DiscussionResponse> replies = discussionEntity.getReplies().stream()
                    .map(item -> entityToResponse(item)).collect(Collectors.toList());
            discussionResponse.setReplies(replies);
        }

        discussionResponse.setCreatedDate(discussionEntity.getCreatedDate());
        discussionResponse.setModifiedDate(discussionEntity.getModifiedDate());
        discussionResponse.setCreatedBy(userService.entityToResponse(discussionEntity.getCreatedBy()));
        discussionResponse.setModifiedBy(userService.entityToResponse(discussionEntity.getModifiedBy()));

        return discussionResponse;
    }

    @Override
    public DiscussionResponse createDiscussion(DiscussionRequest discussionRequest, UserEntity userEntity) {
        log.info("user {} [{}] creating discussion", userEntity.getName(), userEntity.getId());
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setType(discussionRequest.getCategory());
        categoryEntity.setId(discussionRequest.getCategoryId());

        DiscussionEntity discussionEntity = new DiscussionEntity();
        discussionEntity.setTitle(discussionRequest.getTitle());
        discussionEntity.setDescription(discussionRequest.getDescription());
        discussionEntity.setTags(discussionRequest.getTags());
        discussionEntity.setCategory(categoryEntity);

        discussionEntity.setCreatedDate(Common.getCurrentUTCDate());
        discussionEntity.setModifiedDate(Common.getCurrentUTCDate());
        discussionEntity.setCreatedBy(userEntity);
        discussionEntity.setModifiedBy(userEntity);
        discussionEntity = discussionRepository.save(discussionEntity);
        log.info("user {} [{}] created discussion {}", userEntity.getName(), userEntity.getId());
        return entityToResponse(discussionEntity);
    }

    @Override
    public DiscussionResponse fetchDiscussion(String discussionId) throws DiscussionNotFoundException {
        DiscussionEntity discussionEntity = discussionRepository.findById(discussionId).orElse(null);
        if (discussionEntity == null) {
            log.error("discussion {} not found", discussionId);
            throw new DiscussionNotFoundException(discussionId);
        }

        return entityToResponse(discussionEntity);
    }

    @Override
    public List<DiscussionResponse> fetchCategoryDiscussions(CategoryType categoryType, String categoryId) {
        log.info("fetching discussions: category {} and {}", categoryType, categoryId);
        List<DiscussionEntity> discussions = discussionRepository.fetchCategoryDiscussions(categoryType, categoryId);
        List<DiscussionResponse> discussionResponses = discussions.stream().map(item -> entityToResponse(item))
                .collect(Collectors.toList());
        log.info("found {} discussions in category {} {}", discussionResponses.size(), categoryType, categoryId);
        return discussionResponses;
    }

    @Override
    public List<DiscussionResponse> fetchUserCategoryDiscussions(CategoryType categoryType, String categoryId, UserEntity userEntity) {
        log.info("fetching user discussions: user {} category {} {}", categoryType, categoryId, userEntity.getId());
        List<DiscussionEntity> discussions = discussionRepository.fetchUserCategoryDiscussions(categoryType, categoryId, userEntity);
        List<DiscussionResponse> discussionResponses = discussions.stream().map(item -> entityToResponse(item))
                .collect(Collectors.toList());
        log.info("found {} discussions", discussionResponses.size());
        return discussionResponses;
    }

    @Override
    public DiscussionResponse updateDiscussion(String discussionId, DiscussionRequest discussionRequest, UserEntity userEntity)
            throws DiscussionNotFoundException, DiscussionUpdateFailedException {
        log.debug("user {} [{}] updating discussion {}", userEntity.getName(), userEntity.getId(), discussionId);
        DiscussionEntity existing = discussionRepository.findById(discussionId).orElse(null);
        if (existing == null) {
            log.error("discussion {} is not found", discussionId);
            throw new DiscussionNotFoundException(discussionId);
        }

        UpdateResult updateResult = discussionRepository.updateDiscussion(discussionId, discussionRequest, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            throw new DiscussionUpdateFailedException(discussionId);
        }

        DiscussionEntity updated = discussionRepository.findById(discussionId).orElse(null);
        log.debug("user {} [{}] updated discussion {}", userEntity.getName(), userEntity.getId(), discussionId);
        return entityToResponse(updated);
    }

    @Override
    public void deleteDiscussion(String discussionId, UserEntity userEntity) throws DiscussionNotFoundException {
        log.info("user {} [{}] deleting discussion {}", userEntity.getName(), userEntity.getId(), discussionId);
        DiscussionEntity existing = discussionRepository.findById(discussionId).orElse(null);
        if (existing == null) {
            log.error("discussion {} is not found", discussionId);
            throw new DiscussionNotFoundException(discussionId);
        }

        discussionRepository.deleteById(discussionId);
        log.info("user {} [{}] deleted discussion {}", userEntity.getName(), userEntity.getId(), discussionId);
    }
}