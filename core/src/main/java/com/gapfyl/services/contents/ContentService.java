package com.gapfyl.services.contents;

import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.exceptions.contents.ContentNotFoundException;
import com.gapfyl.exceptions.contents.ContentUpdateFailedException;
import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 27/04/21
 **/

public interface ContentService {

    ContentResponse entityToDto(ContentEntity contentEntity);

    ContentResponse createContent(ContentRequest contentRequest, UserEntity userEntity);

    ContentResponse updateContent(String contentId, ContentRequest contentRequest, UserEntity userEntity)
            throws ContentNotFoundException, ContentUpdateFailedException;

    void deleteContent(String contentId, UserEntity userEntity) throws ContentNotFoundException;

}
