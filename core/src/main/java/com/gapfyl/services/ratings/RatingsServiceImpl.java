package com.gapfyl.services.ratings;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.models.common.CategoryEntity;
import com.gapfyl.models.ratings.RatingsEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.RatingsRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

@Slf4j
@Service
public class RatingsServiceImpl implements RatingsService {

    @Autowired
    RatingsRepository ratingsRepository;

    @Override
    public void addRatings(CategoryEntity category, int ratings, UserEntity userEntity) {
        RatingsEntity ratingsEntity = new RatingsEntity();
        ratingsEntity.setCategory(category);
        ratingsEntity.setRatings(ratings);
        ratingsEntity.setCreatedBy(userEntity);
        ratingsEntity.setModifiedBy(userEntity);
        ratingsEntity.setCreatedDate(Common.getCurrentUTCDate());
    }

    @Override
    public RatingsEntity fetchUserCategoryRatings(CategoryType categoryType, String categoryId, UserEntity userEntity) {
        log.info("fetch user {} rated a category {} {}", userEntity.getId(), categoryType, categoryId);
        return ratingsRepository.fetchUserCategoryRatings(categoryType, categoryId, userEntity);
    }

    @Override
    public List<RatingsEntity> fetchCategoryRatings(CategoryType categoryType, String categoryId) {
        log.info("fetch category ratings {} {}", categoryType, categoryId);
        return ratingsRepository.fetchCategoryRatings(categoryType, categoryId);
    }

}
