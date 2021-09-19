package com.gapfyl.repository.custom;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.models.ratings.RatingsEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

public interface ICustomRatingsRepository {

    RatingsEntity fetchUserCategoryRatings(CategoryType categoryType, String categoryId, UserEntity userEntity);

    List<RatingsEntity> fetchCategoryRatings(CategoryType categoryType, String categoryId);
}
