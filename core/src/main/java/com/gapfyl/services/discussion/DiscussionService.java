package com.gapfyl.services.discussion;

import com.gapfyl.dto.discussions.DiscussionResponse;
import com.gapfyl.dto.discussions.DiscussionRequest;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.discussions.DiscussionNotFoundException;
import com.gapfyl.exceptions.discussions.DiscussionUpdateFailedException;
import com.gapfyl.models.users.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface DiscussionService {

    DiscussionResponse createDiscussion(DiscussionRequest discussionRequest, UserEntity userEntity);

    DiscussionResponse fetchDiscussion(String discussionId) throws DiscussionNotFoundException;

    List<DiscussionResponse> fetchCategoryDiscussions(CategoryType category, String categoryId);

    List<DiscussionResponse> fetchUserCategoryDiscussions(CategoryType category, String categoryId, UserEntity userEntity);

    DiscussionResponse updateDiscussion(String discussionId, DiscussionRequest discussionRequest, UserEntity userEntity)
            throws DiscussionNotFoundException, DiscussionUpdateFailedException;

    void deleteDiscussion(String discussionId, UserEntity userEntity) throws DiscussionNotFoundException;

}
