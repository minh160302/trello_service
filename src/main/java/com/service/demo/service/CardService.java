package com.service.demo.service;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.CardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface CardService {
  /**
   * Get information of one card
   * RECOMMEND: call this after change card's database
   *
   * @param id
   * @return
   */
  BaseResponseDTO<CardResponseDTO> get(String id);

  BaseResponseDTO<String> create(CardRequestDTO cardRequestDTO);

  BaseResponseDTO<CardResponseDTO> update(CardRequestDTO cardRequestDTO);

  BaseResponseDTO<String> delete(String id);
}
