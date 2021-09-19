package com.gapfyl.repository;

import com.gapfyl.models.users.RegistrationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 14/04/21
 **/

public interface RegistrationRepository extends MongoRepository<RegistrationEntity, String> {

    RegistrationEntity findOneByEmail(String email);

    RegistrationEntity findOneByActivationToken(String activationToken);
}
