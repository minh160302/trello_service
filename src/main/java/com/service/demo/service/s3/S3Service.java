package com.service.demo.service.s3;

import com.amazonaws.AmazonServiceException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface S3Service {
  String upload(String folder, File file) throws AmazonServiceException;

  String getEndpoint();
}
