package com.service.demo.service;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface CardService {
  BaseResponseDTO<String> create(CardRequestDTO cardRequestDTO);
}
