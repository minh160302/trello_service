package com.service.demo.service;

import com.service.demo.dto.request.WorkspaceRequestDTO;
import com.service.demo.dto.response.WorkspaceResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface WorkspaceService {
  BaseResponseDTO<String> create(WorkspaceRequestDTO workspaceRequestDTO);

  BaseResponseDTO<WorkspaceResponseDTO> get(String email);
}
