package com.service.demo.service;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.request.util.MoveCardRequestDTO;
import com.service.demo.dto.response.CatalogResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface CatalogService {
  BaseResponseDTO<String> create(CatalogRequestDTO catalogRequestDTO);

  BaseResponseDTO<CatalogResponseDTO> update(CatalogRequestDTO catalogRequestDTO);

  BaseResponseDTO<String> delete(String id);

  BaseResponseDTO<String> move(MoveCardRequestDTO moveCardRequestDTO);
}
