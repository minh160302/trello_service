package com.service.demo.controller;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
  @Autowired
  private CatalogService catalogService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody CatalogRequestDTO catalogRequestDTO) {
    return catalogService.create(catalogRequestDTO);
  }
}
