package com.gapfyl.repository;

import com.gapfyl.models.users.UserCourseEntity;
import com.gapfyl.repository.custom.ICustomUserCourseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

public interface UserCourseRepository extends MongoRepository<UserCourseEntity, String>, ICustomUserCourseRepository {
}
