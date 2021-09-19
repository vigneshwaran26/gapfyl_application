package com.gapfyl.services.files;

import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.assets.AssetUploadFailedException;
import com.gapfyl.exceptions.users.AccountNotFoundException;
import com.gapfyl.models.users.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(CategoryType categoryType, String categoryId, MultipartFile multipartFile, UserEntity userEntity)
            throws AssetUploadFailedException, AccountNotFoundException;
}
