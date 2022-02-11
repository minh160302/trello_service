package com.service.demo.service.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.service.demo.exception.InvalidResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service{
  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Autowired(required = true)
  private AmazonS3 amazonS3;

  /**
   *
   * @param folder
   * @param file
   * @return
   * @throws AmazonServiceException
   * Upload image/file to S3
   */
  @Override
  @Async
  public String upload(String folder, File file) throws AmazonServiceException {
    try {
      String keyName = String.format("%s/%s", folder, file.getName());
      this.amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file));
      //delete file
      file.delete();
      return keyName;
    } catch (AmazonServiceException e) {
      throw new InvalidResourceException(e.getErrorMessage(), file);
    }
  }

  @Override
  public String getEndpoint() {
    return String.format("https://%s.s3.%s.amazonaws.com", bucketName, this.amazonS3.getBucketLocation(bucketName));
  }
}
