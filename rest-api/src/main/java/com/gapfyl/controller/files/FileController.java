package com.gapfyl.controller.files;

import com.gapfyl.controller.common.AbstractController;
import com.gapfyl.enums.common.CategoryType;
import com.gapfyl.exceptions.assets.AssetUploadFailedException;
import com.gapfyl.exceptions.users.AccountNotFoundException;
import com.gapfyl.services.files.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/1.0/files")
public class FileController extends AbstractController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    ResponseEntity uploadFile(@RequestParam("categoryType")CategoryType categoryType,
                              @RequestParam("categoryId") String categoryId,
                              @RequestBody MultipartFile multipartFile) throws AssetUploadFailedException, AccountNotFoundException {

        return ResponseEntity.ok().body(fileService.uploadFile(categoryType, categoryId, multipartFile, getCurrentUser()));
    }
}
