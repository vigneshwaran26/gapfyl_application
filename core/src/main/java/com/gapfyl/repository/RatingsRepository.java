package com.gapfyl.repository;

import com.gapfyl.models.ratings.RatingsEntity;
import com.gapfyl.repository.custom.ICustomRatingsRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 03/08/21
 **/

public interface RatingsRepository extends MongoRepository<RatingsEntity, String>, ICustomRatingsRepository {
}
