package com.gapfyl.repository;

import com.gapfyl.models.discussions.DiscussionEntity;
import com.gapfyl.repository.custom.ICustomDiscussionRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscussionRepository extends MongoRepository<DiscussionEntity, String>, ICustomDiscussionRepository {

}
