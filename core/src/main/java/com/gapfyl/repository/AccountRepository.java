package com.gapfyl.repository;

import com.gapfyl.models.users.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 30/04/21
 **/

public interface AccountRepository extends MongoRepository<AccountEntity, String> {

    AccountEntity findOneByName(String name);
}
