package com.gapfyl.repository;


import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomUserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String>, ICustomUserRepository {

    UserEntity findOneByEmail(String email);

    UserEntity findOneByRecoveryToken(String recoveryToken);

}
