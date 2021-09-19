package com.gapfyl.services.users.creators;

import com.gapfyl.dto.users.creators.CreatorRequest;
import com.gapfyl.dto.users.creators.CreatorResponse;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

public interface CreatorService {

    CreatorEntity fetchById(String creatorId);

    CreatorEntity fetchByUserId(String userId);

    CreatorResponse fetchUserCreatorProfile(UserEntity userEntity);

    CreatorResponse createOrUpdateCreator(CreatorRequest creatorCreateRequest, UserEntity userEntity) throws NotFoundException;

}
