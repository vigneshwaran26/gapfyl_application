package com.gapfyl.services.amazon;

import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.amazonaws.util.IOUtils;
import com.gapfyl.constants.MetadataCode;
import com.gapfyl.exceptions.GapFylException;
import com.gapfyl.exceptions.assets.AssetUploadFailedException;
import com.gapfyl.exceptions.assets.ReportUploadFailedException;
import com.gapfyl.exceptions.assets.S3ObjectCopyFailedException;
import com.gapfyl.exceptions.assets.S3ObjectDeleteFailedException;
import com.gapfyl.exceptions.assets.S3ObjectMoveFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * @author vignesh
 * Created on 20/04/21
 **/

@Slf4j
@Service
public class AmazonServiceImpl implements AmazonService {

    @Value("${cloud.aws.s3.bucket.in:no-bucket}")
    private String bucketIn;

    @Autowired
    @Qualifier("amazonS3")
    private AmazonS3 amazonS3;

    public void uploadFile(MultipartFile file, String key) throws AssetUploadFailedException {
        if (file == null) throw new IllegalArgumentException("File is null");
        if (key == null) throw new IllegalArgumentException("Key is null");

        log.info("uploading file {} to key {}", file.getOriginalFilename(), key);
        long startTime = System.currentTimeMillis();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            metadata.setContentDisposition(String.format("attachment;filename=\"%s\"", file.getOriginalFilename()));
            metadata.addUserMetadata(MetadataCode.ORIGINAL_NAME, file.getOriginalFilename());

            Upload uploadTransaction = transferManager.upload(bucketIn, key, file.getInputStream(), metadata);
            UploadResult uploadResult = uploadTransaction.waitForUploadResult();
            log.info("file {} to key {} uploaded successfully", file.getOriginalFilename(), key);
            log.info("time spent uploading file of size {}: {}s", file.getSize(), (System.currentTimeMillis() - startTime / 1000));
        } catch (InterruptedException | IOException e) {
            log.error("failed to upload file {} {}", file.getOriginalFilename(), e.getMessage());
            throw new AssetUploadFailedException(file.getOriginalFilename(), e);
        } catch (AmazonS3Exception e) {
            log.error("failed to upload file {} {}", file.getOriginalFilename(), e.getErrorMessage());
            throw new AssetUploadFailedException(file.getOriginalFilename(), e);
        }
    }

    public void uploadFile(File file, String key) throws AssetUploadFailedException {
        if (file == null) throw new IllegalArgumentException("file is null");
        if (key == null) throw new IllegalArgumentException("key is null");

        log.info("uploading file {} to key {}", file.getName(), key);
        long startTime = System.currentTimeMillis();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType(Files.probeContentType(file.toPath()));
            metadata.addUserMetadata(MetadataCode.ORIGINAL_NAME, file.getName());

            FileInputStream inputStream = new FileInputStream(file);
            Upload uploadTransaction = transferManager.upload(bucketIn, key, inputStream, metadata);
            UploadResult uploadResult = uploadTransaction.waitForUploadResult();
            log.info("file {} to key {} uploaded successfully", file.getName(), key);
            log.info("time spent uploading file of size {}: {}s", file.length(),
                    (System.currentTimeMillis() - startTime / 1000));

        } catch (InterruptedException | IOException e) {
            throw new AssetUploadFailedException(file.getName(), e);
        }
    }

    public void uploadFile(InputStream inputStream, String key, ObjectMetadata metadata) throws AssetUploadFailedException {
        if (inputStream == null) throw new IllegalArgumentException("inputStream is null");
        if (key == null) throw new IllegalArgumentException("key is null");

        log.info("uploading inputStream to key {}", key);
        long startTime = System.currentTimeMillis();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        try {
            Upload uploadTransaction = transferManager.upload(bucketIn, key, inputStream, metadata);
            UploadResult uploadResult = uploadTransaction.waitForUploadResult();
        } catch (InterruptedException e) {
            throw new AssetUploadFailedException("Input stream", e);
        }
    }

    public void uploadFile(InputStream inputStream, String key) throws AssetUploadFailedException {
        this.uploadFile(inputStream, key, new ObjectMetadata());
    }

    public void uploadCSVFile(File file, String key, String fileName) throws ReportUploadFailedException {
        if (file == null) throw new IllegalArgumentException("file is null");

        log.info("uploading file {} to key {}", fileName, key);
        long startTime = System.currentTimeMillis();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setContentType("application/csv;charset=UTF-8");
            metadata.setContentDisposition(String.format("attachment;filename=\"%s\"", fileName));

            FileInputStream inputStream = new FileInputStream(file);

            Upload uploadTransaction = transferManager.upload(bucketIn, key, inputStream, metadata);
            UploadResult uploadResult = uploadTransaction.waitForUploadResult();
            log.info("file {} to key {} uploaded successfully", fileName, key);
            log.info("time spent uploading file of size {}: {}s", file.length(),
                    (System.currentTimeMillis() - startTime) / 1000);
        } catch (InterruptedException | IOException e) {
            throw new ReportUploadFailedException(fileName, e);
        }
     }

    public void copyObject(String source, String destination) throws S3ObjectCopyFailedException {
        if (source == null) throw new IllegalArgumentException("source key cannot be null");
        if (destination == null) throw new IllegalArgumentException("destination key cannot be null");

        log.info("copying the S3 object from {} to {}", source, destination);
        try {
            amazonS3.copyObject(bucketIn, source, bucketIn, destination);
        } catch (SdkClientException e) {
            log.error("unable to copy S3 object", e);
            throw new S3ObjectCopyFailedException(source, destination);
        }
    }

    public void deleteObject(String source) throws S3ObjectDeleteFailedException {
        if (source == null) throw new IllegalArgumentException("source cannot be null");

        log.info("deleting the S3 object {}", source);
        try {
            amazonS3.deleteObject(bucketIn, source);
        } catch (SdkClientException e) {
            log.error("unable to delete S3 object", e);
            throw new S3ObjectDeleteFailedException(source);
        }
    }

    public void moveObject(String source, String destination) throws S3ObjectMoveFailedException {
        if (source == null) throw new IllegalArgumentException("source key cannot be null");
        if (destination == null) throw new IllegalArgumentException("destination key cannot be null");

        log.info("moving the S3 object from {} to {}", source, destination);
        try {
            this.copyObject(source, destination);
            this.deleteObject(source);
        } catch (GapFylException | SdkClientException e) {
            log.error("unable to move S3 object {}", e);
            throw new S3ObjectMoveFailedException(source, destination, e);
        }
    }

    public String downloadFile(String url) throws IOException {
        log.debug("downloading file for url {}", url);
        S3Object s3Object = amazonS3.getObject(bucketIn, url);
        byte[] imgBytes = IOUtils.toByteArray(s3Object.getObjectContent());

        String img = "data:image/png;base64," + Base64.getEncoder().encodeToString(imgBytes);
        log.debug("downloaded file for url {}", url);
        return img;
    }

    public String downloadFileAsString(String url) throws IOException {
        log.debug("downloading file for url {}", url);
        S3Object s3Object = amazonS3.getObject(bucketIn, url);
        String content = IOUtils.toString(s3Object.getObjectContent());

        log.debug("downloaded file for url {}", url);
        return content;
    }

    public InputStream downloadFileAsInputStream(String url) {
        log.debug("downloading file for url {}", url);
        S3Object s3Object = amazonS3.getObject(bucketIn, url);
        InputStream inputStream = s3Object.getObjectContent();
        log.debug("downloaded file for url {}", url);
        return inputStream;
    }

    public URL generatePreSignedURL(String key, String fileName) {
        log.debug("generating pre signed url for key {}", key);
        Date expiration = new Date();
        long milliseconds = expiration.getTime();
        milliseconds += 1000 * 60 * 60;
        expiration.setTime(milliseconds);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketIn, key);
        generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
        generatePresignedUrlRequest.setExpiration(expiration);
        generatePresignedUrlRequest.addRequestParameter(Headers.S3_USER_METADATA_PREFIX +
                        MetadataCode.ORIGINAL_NAME, fileName);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        log.info("generated pre signed url for key {}: {}", key, url);


        return url;
    }

    public void deleteExpiredObjectsInFolder(String key, LocalDate expiredSince) {
        log.debug("deleting expired url for folder {} expired since {}", key, expiredSince);
        ObjectListing objectListing = amazonS3.listObjects(bucketIn, key);
        for (S3ObjectSummary s3Object: objectListing.getObjectSummaries()) {
            LocalDate urlCreatedDate = Instant.ofEpochMilli(s3Object.getLastModified().getTime())
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            if (urlCreatedDate.isBefore(expiredSince)) {
                try {
                    deleteObject(s3Object.getKey());
                } catch (S3ObjectDeleteFailedException e) {
                    e.printStackTrace();
                }
            }
        }

        log.debug("deleted expired url for key {}", key);
    }
}
