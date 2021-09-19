package com.gapfyl.repository.custom;

import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.models.users.UserEntity;
import com.mongodb.client.result.UpdateResult;

/**
 * @author vignesh
 * Created on 01/05/21
 **/

public interface ICustomContentRepository {

    UpdateResult updateContent(String contentId, ContentRequest contentRequest, UserEntity userEntity);
}
