package com.gapfyl.services.users.learners;

import com.gapfyl.dto.users.learners.LearnerRequest;
import com.gapfyl.dto.users.learners.LearnerResponse;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 14/08/21
 **/

public interface LearnerService {

    LearnerResponse createOrUpdateLearner(LearnerRequest learnerRequest, UserEntity userEntity) throws NotFoundException;

    LearnerResponse fetchUserLearnerProfile(UserEntity userEntity);
}
