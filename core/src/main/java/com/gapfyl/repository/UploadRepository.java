package com.gapfyl.repository;

import com.gapfyl.models.uploads.UploadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author vignesh
 * Created on 25/06/21
 **/

public interface UploadRepository extends MongoRepository<UploadEntity, String> {
}
