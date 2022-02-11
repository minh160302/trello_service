package com.service.demo.service.impl;

import com.service.demo.exception.BadRequestException;
import com.service.demo.model.Upload;
import com.service.demo.service.UploadService;
import com.service.demo.service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UploadServiceImpl implements UploadService {
  private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  @Autowired(required = true)
  private S3Service s3Service;

  @Override
  public Upload post(MultipartFile multipartFile) {
    File file = this.convertFile(multipartFile);
    LocalDate date = LocalDate.now();
    String folder = String.format("%s/%s/%s", date.getYear(), date.getMonthValue(), date.getDayOfMonth());

    String keyName = s3Service.upload(folder, file);
    String endpoint = s3Service.getEndpoint();
    String path = String.format("%s/%s", endpoint, keyName);

    Upload upload = new Upload();
    upload.setKey(keyName);
    upload.setPath(path);
    upload.setName(file.getName());
    upload.setType(this.getNameExtension(file.getName()));

    return upload;
  }

  @Override
  public File convertFile(MultipartFile file) {
    String extension = this.getNameExtension(Objects.requireNonNull(file.getOriginalFilename()));
    String fileName = this.getFileName(Objects.requireNonNull(file.getOriginalFilename()));
    String newFileName = toSlug(fileName) + extension;

    File newFile = new File(newFileName);
    try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
      outputStream.write(file.getBytes());
    } catch (IOException ex) {
      throw new BadRequestException("Error converting the multi-part file to file=" + ex.getMessage());
    }
    return newFile;
  }

  @Override
  public String getNameExtension(String text) {
    int index = text.lastIndexOf(".");
    if (index == -1) return ""; // empty extension
    return text.substring(index);
  }

  @Override
  public String getFileName(String text) {
    int index = text.lastIndexOf(".");
    if (index == -1) return ""; // empty extension
    return text.substring(0, index);
  }

  @Override
  public String toSlug(String input) {
    String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
    String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
    String slug = NONLATIN.matcher(normalized).replaceAll("");
    return slug.toLowerCase(Locale.ENGLISH);
  }
}
