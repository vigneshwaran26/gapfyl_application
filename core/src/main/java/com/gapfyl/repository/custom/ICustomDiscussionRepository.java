package com.gapfyl.repository.custom;

import com.gapfyl.dto.discussions.DiscussionRequest;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.discussions.DiscussionEntity;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

public interface ICustomDiscussionRepository {

    List<DiscussionEntity> fetchCategoryDiscussions(CategoryType category, String categoryId);

    List<DiscussionEntity> fetchUserCategoryDiscussions(CategoryType category, String categoryId, UserEntity userEntity);

    UpdateResult updateDiscussion(String discussionId, DiscussionRequest discussionRequest, UserEntity userEntity);

}
