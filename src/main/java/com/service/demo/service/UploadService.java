package com.service.demo.service;

import com.service.demo.model.Upload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UploadService {
  Upload post(MultipartFile multipartFile);

  File convertFile(MultipartFile multipartFile);

  String getNameExtension(String text);

  String getFileName(String text);

  String toSlug(String input);
}
