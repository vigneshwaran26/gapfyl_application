package com.gapfyl.repository;

import com.gapfyl.models.users.learners.LearnerEntity;
import com.gapfyl.repository.custom.ICustomLearnerRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 14/08/21
 **/

public interface LearnerRepository extends MongoRepository<LearnerEntity, String>, ICustomLearnerRepository {

    LearnerEntity findOneByUserId(String userId);

}
