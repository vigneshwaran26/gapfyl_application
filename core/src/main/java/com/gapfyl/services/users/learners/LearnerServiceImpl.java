package com.gapfyl.services.users.learners;

import com.gapfyl.dto.users.learners.LearnerRequest;
import com.gapfyl.dto.users.learners.LearnerResponse;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.UserRole;
import com.gapfyl.models.users.learners.LearnerEntity;
import com.gapfyl.repository.LearnerRepository;
import com.gapfyl.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author vignesh
 * Created on 14/08/21
 **/

@Slf4j
@Service
public class LearnerServiceImpl implements LearnerService {

    @Autowired
    LearnerRepository learnerRepository;

    @Autowired
    UserService userService;

    private LearnerResponse entityToResponse(LearnerEntity learnerEntity) {
        LearnerResponse learnerResponse = new LearnerResponse();
        learnerResponse.setAbout(learnerEntity.getAbout());
        learnerResponse.setInterests(learnerEntity.getInterests());
        learnerResponse.setLanguages(learnerEntity.getLanguages());
        return learnerResponse;
    }

    public LearnerEntity fetchById(String id) {
        return learnerRepository.findById(id).orElse(null);
    }

    public LearnerEntity fetchByUserId(String userId) {
        return learnerRepository.findOneByUserId(userId);
    }

    private LearnerEntity createLearner(LearnerRequest learnerRequest, UserEntity userEntity) {
        LearnerEntity learnerEntity = new LearnerEntity();
        learnerEntity.setAccountId(userEntity.getAccount().getId());
        learnerEntity.setUserId(userEntity.getId());
        learnerEntity.setInterests(learnerRequest.getInterests());
        learnerEntity.setLanguages(learnerRequest.getLanguages());
        learnerEntity = learnerRepository.save(learnerEntity);
        return learnerEntity;
    }

    private LearnerEntity updateLearner(String learnerId, LearnerRequest learnerRequest, UserEntity userEntity) {
        return learnerRepository.updateLearner(learnerId, learnerRequest, userEntity);
    }

    @Override
    public LearnerResponse createOrUpdateLearner(LearnerRequest learnerRequest, UserEntity userEntity)
            throws NotFoundException {

        log.debug("user {} [{}] set profile type as learner", userEntity.getName(), userEntity.getId());
        LearnerEntity existing = fetchByUserId(userEntity.getId());

        LearnerEntity learnerEntity = (existing == null) ? createLearner(learnerRequest, userEntity) :
                updateLearner(existing.getId(), learnerRequest, userEntity);

        if (learnerEntity == null) {

        }

        // update user profile type as learner
        userService.changeProfileType(userEntity.getId(), ProfileType.learner, userEntity);

        // update user roles as learner
        userService.updateRoles(userEntity.getEmail(), Arrays.asList(UserRole.ROLE_LEARNER.name()),
                Arrays.asList(UserRole.ROLE_CREATOR.name(), UserRole.ROLE_ORGANIZATION.name(),
                        UserRole.ROLE_SCHOOL.name()), userEntity);

        return entityToResponse(learnerEntity);
    }

    @Override
    public LearnerResponse fetchUserLearnerProfile(UserEntity userEntity) {
        LearnerEntity learnerEntity = fetchByUserId(userEntity.getId());
        if (learnerEntity == null) return null;
        return entityToResponse(learnerEntity);
    }
}
