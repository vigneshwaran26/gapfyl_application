package com.gapfyl.services.contents;

import com.gapfyl.dto.contents.ContentResponse;
import com.gapfyl.dto.contents.ContentRequest;
import com.gapfyl.exceptions.contents.ContentNotFoundException;
import com.gapfyl.exceptions.contents.ContentUpdateFailedException;
import com.gapfyl.models.contents.ContentEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.ContentRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vignesh
 * Created on 27/04/21
 **/

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    ContentRepository contentRepository;

    @Override
    public ContentResponse entityToDto(ContentEntity contentEntity) {
        ContentResponse contentResponseDTO = new ContentResponse();
        contentResponseDTO.setId(contentEntity.getId());
        contentResponseDTO.setTitle(contentEntity.getTitle());
        contentResponseDTO.setDescription(contentEntity.getDescription());
        contentResponseDTO.setContentType(contentEntity.getContentType());
        contentResponseDTO.setContentUrl(contentEntity.getContentUrl());
        contentResponseDTO.setCreatedDate(contentEntity.getCreatedDate());
        contentResponseDTO.setModifiedDate(contentEntity.getModifiedDate());
        return contentResponseDTO;
    }

    @Override
    public ContentResponse createContent(ContentRequest contentRequest, UserEntity userEntity) {
        log.info("user {} [{}] creating content", userEntity.getName(), userEntity.getId());
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.setTitle(contentRequest.getTitle());
        contentEntity.setDescription(contentRequest.getDescription());
        contentEntity.setContentType(contentRequest.getContentType());
        contentEntity.setContentUrl(contentRequest.getContentUrl());
        contentEntity.setCreatedDate(Common.getCurrentUTCDate());
        contentEntity.setCreatedBy(userEntity);
        contentEntity.setModifiedDate(Common.getCurrentUTCDate());
        contentEntity.setModifiedBy(userEntity);
        ContentEntity addedContent = contentRepository.save(contentEntity);
        log.info("user {} [{}] created content", userEntity.getName(), userEntity.getId());
        return entityToDto(addedContent);
    }

    @Override
    public ContentResponse updateContent(String contentId, ContentRequest contentRequest, UserEntity userEntity)
            throws ContentNotFoundException, ContentUpdateFailedException {

        log.info("user {} [{}] updating content {}", userEntity.getName(), userEntity.getId(), contentId);
        ContentEntity existing = contentRepository.findById(contentId).orElse(null);
        if (existing == null) {
            log.error("content {} not found", contentId);
            throw new ContentNotFoundException(contentId);
        }

        UpdateResult updateResult = contentRepository.updateContent(contentId, contentRequest, userEntity);
        if (updateResult.getModifiedCount() == 0) {
            log.error("content {} failed to update", contentId);
            throw new ContentUpdateFailedException(contentId);
        }

        ContentEntity updated = contentRepository.findById(contentId).orElse(null);
        log.info("user {} [{}] updated content {}", userEntity.getName(), userEntity.getId(), contentId);
        return entityToDto(updated);
    }

    @Override
    public void deleteContent(String contentId, UserEntity userEntity) throws ContentNotFoundException {
        log.info("user {} [{}] deleting the content {}", userEntity.getName(), userEntity.getId());
        ContentEntity content = contentRepository.findById(contentId).orElse(null);
        if (content == null) {
            log.error("content {} not found", contentId);
            throw new ContentNotFoundException(contentId);
        }

        contentRepository.deleteById(contentId);
        log.info("user {} [{}] deleted the content {}", userEntity.getName(), userEntity.getId(), contentId);
    }
}
