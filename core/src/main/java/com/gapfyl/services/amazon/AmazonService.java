package com.gapfyl.services.amazon;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gapfyl.exceptions.assets.AssetUploadFailedException;
import com.gapfyl.exceptions.assets.ReportUploadFailedException;
import com.gapfyl.exceptions.assets.S3ObjectCopyFailedException;
import com.gapfyl.exceptions.assets.S3ObjectDeleteFailedException;
import com.gapfyl.exceptions.assets.S3ObjectMoveFailedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

public interface AmazonService {

    void uploadFile(MultipartFile file, String key) throws AssetUploadFailedException;

    void uploadFile(File file, String key) throws AssetUploadFailedException;

    void uploadFile(InputStream inputStream, String key, ObjectMetadata metadata) throws AssetUploadFailedException;

    void uploadFile(InputStream inputStream, String key) throws AssetUploadFailedException;

    void uploadCSVFile(File file, String key, String fileName) throws ReportUploadFailedException;

    void copyObject(String source, String destination) throws S3ObjectCopyFailedException;

    void deleteObject(String source) throws S3ObjectDeleteFailedException;

    void moveObject(String source, String destination) throws S3ObjectMoveFailedException;

    String downloadFile(String url) throws IOException;

    String downloadFileAsString(String url) throws IOException;

    InputStream downloadFileAsInputStream(String url) throws IOException;

    URL generatePreSignedURL(String key, String fileName);

    void deleteExpiredObjectsInFolder(String key, LocalDate expiredSince);
}
