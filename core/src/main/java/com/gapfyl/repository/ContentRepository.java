package com.gapfyl.repository;

import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.repository.custom.ICustomContentRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 01/05/21
 **/

public interface ContentRepository extends MongoRepository<ContentEntity, String>, ICustomContentRepository {
}
