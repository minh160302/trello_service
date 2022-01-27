package com.service.demo.service;

import com.service.demo.dto.request.BoardRequestDTO;
import com.service.demo.dto.response.BoardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface BoardService {
  BaseResponseDTO<String> create(BoardRequestDTO boardRequestDTO);

  BaseResponseDTO<BoardResponseDTO> getById(String id);
}
