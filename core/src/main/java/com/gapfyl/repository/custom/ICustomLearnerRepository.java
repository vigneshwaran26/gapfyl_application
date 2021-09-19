package com.gapfyl.repository.custom;

import com.gapfyl.dto.users.learners.LearnerRequest;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.learners.LearnerEntity;

/**
 * @author vignesh
 * Created on 15/08/21
 **/

public interface ICustomLearnerRepository {

    LearnerEntity updateLearner(String learnerId, LearnerRequest learnerRequest, UserEntity userEntity);

}
