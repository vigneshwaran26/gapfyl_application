package com.gapfyl.repository;

import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.repository.custom.ICustomCreatorRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

public interface CreatorRepository extends MongoRepository<CreatorEntity, String>, ICustomCreatorRepository {

    CreatorEntity findOneByUserId(String userId);
}
