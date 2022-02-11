package com.service.demo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
  @Value("${aws.s3.access-key}")
  private String accessKey;

  @Value("${aws.s3.secret-key}")
  private String secretKey;

  @Bean
  public AmazonS3 s3() {
    AWSCredentials awsCredentials =
            new BasicAWSCredentials(accessKey, secretKey);
    return AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.US_EAST_2)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
  }
}
