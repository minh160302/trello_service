package com.service.demo.service;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface CatalogService {
  BaseResponseDTO<String> create(CatalogRequestDTO catalogRequestDTO);
}
