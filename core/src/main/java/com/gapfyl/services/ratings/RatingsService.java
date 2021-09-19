package com.gapfyl.services.ratings;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.models.common.CategoryEntity;
import com.gapfyl.models.ratings.RatingsEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

public interface RatingsService {

    void addRatings(CategoryEntity category, int Ratings, UserEntity userEntity);

    RatingsEntity fetchUserCategoryRatings(CategoryType categoryType, String categoryId, UserEntity userEntity);

    List<RatingsEntity> fetchCategoryRatings(CategoryType categoryType, String categoryId);
}
