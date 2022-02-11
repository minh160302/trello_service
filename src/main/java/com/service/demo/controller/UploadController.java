package com.service.demo.controller;

import com.service.demo.model.Upload;
import com.service.demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
public class UploadController {
  @Autowired
  private UploadService uploadService;

  @PostMapping
  public Upload post(@RequestPart(value = "file") MultipartFile multipartFile){
    return uploadService.post(multipartFile);
  }
}
