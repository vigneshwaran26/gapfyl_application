package com.gapfyl.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author vignesh
 * Created on 28/04/21
 **/

@Configuration
public class AWSConfig {

    @Autowired
    private Environment environment;

    @Bean
    public AmazonS3 amazonS3() {
        String accessKey = environment.getProperty("cloud.aws.credentials.access-key");
        String secretKey = environment.getProperty("cloud.aws.credentials.secret-key");
        String region = environment.getProperty("cloud.aws.region.static");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }
}
