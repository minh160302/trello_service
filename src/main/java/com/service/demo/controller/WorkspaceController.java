package com.service.demo.controller;

import com.service.demo.dto.request.WorkspaceRequestDTO;
import com.service.demo.dto.response.WorkspaceResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController {
  @Autowired
  private WorkspaceService workspaceService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody WorkspaceRequestDTO workspaceRequestDTO) {
    return workspaceService.create(workspaceRequestDTO);
  }

  // get a bunch of information too
  @GetMapping("/{email}")
  public BaseResponseDTO<WorkspaceResponseDTO> get(@PathVariable String email) {
    return workspaceService.get(email);
  }
}
