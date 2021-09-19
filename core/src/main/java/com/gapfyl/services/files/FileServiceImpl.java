package com.gapfyl.services.files;

import com.gapfyl.builders.URLBuilder;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.assets.AssetUploadFailedException;
import com.gapfyl.exceptions.users.AccountNotFoundException;
import com.gapfyl.enums.common.FileType;
import com.gapfyl.models.common.CategoryEntity;
import com.gapfyl.models.uploads.UploadEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.UploadRepository;
import com.gapfyl.services.amazon.AmazonService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    AmazonService amazonService;

    @Autowired
    UploadRepository uploadRepository;

    private static FileType getFileType(String contentType) {
        if (contentType.contains("image")) return FileType.image;
        else if (contentType.contains("video")) return FileType.video;
        else if (contentType.contains("pdf")) return FileType.pdf;
        else return FileType.asset;
    }

    private static String generateKey(String accountId, FileType fileType) {
        URLBuilder urlBuilder = URLBuilder.upload();
        String fileId = UUID.randomUUID().toString();
        if (fileType.equals(FileType.image)) urlBuilder = URLBuilder.image().withImage(fileId);
        else if (fileType.equals(FileType.video)) urlBuilder = URLBuilder.video().withVideo(fileId);
        return urlBuilder.withAccount(accountId).withUniqueId().build();
    }

    public String uploadFile(CategoryType categoryType, String categoryId, MultipartFile multipartFile, UserEntity userEntity)
            throws AssetUploadFailedException, AccountNotFoundException {

        String accountId = userEntity.getAccount().getId();
        if (accountId == null) {
            log.error("user account id is null");
            throw new AccountNotFoundException("user account id is null");
        }

        FileType fileType = getFileType(multipartFile.getContentType());
        String key = generateKey(accountId, fileType);
        amazonService.uploadFile(multipartFile, key);
        saveUpload(categoryType, categoryId, multipartFile.getOriginalFilename(), fileType, key, userEntity);
        return key;
    }

    @Async("gapFylTaskExecutor")
    private void saveUpload(CategoryType categoryType, String categoryId, String fileName,
                                   FileType fileType, String key,  UserEntity userEntity) {

        log.info("adding uploaded file details {} {}", fileName, fileType);
        CategoryEntity category = new CategoryEntity();
        category.setType(categoryType);
        category.setId(categoryId);

        UploadEntity upload = new UploadEntity();
        upload.setName(fileName);
        upload.setFileType(fileType);
        upload.setUploadKey(key);
        upload.setCategory(category);
        upload.setCreatedBy(userEntity);
        upload.setModifiedBy(userEntity);
        upload.setCreatedDate(Common.getCurrentUTCDate());
        upload.setModifiedDate(Common.getCurrentUTCDate());
        uploadRepository.save(upload);
        log.info("added uploaded file details");
    }
}
