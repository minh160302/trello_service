package com.service.demo.service.impl;

import com.service.demo.dto.request.WorkspaceRequestDTO;
import com.service.demo.dto.response.BoardResponseDTO;
import com.service.demo.dto.response.WorkspaceResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Board;
import com.service.demo.model.Workspace;
import com.service.demo.repository.BoardRepository;
import com.service.demo.repository.WorkspaceRepository;
import com.service.demo.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {
  @Autowired
  private WorkspaceRepository workspaceRepository;

  @Autowired
  private BoardRepository boardRepository;

  @Override
  public BaseResponseDTO<String> create(WorkspaceRequestDTO workspaceRequestDTO) {
    Workspace workspace = this.modelMapper(workspaceRequestDTO);
    workspaceRepository.save(workspace);
    return new BaseResponseDTO("Create workspace successfully", HttpStatus.CREATED);
  }

  @Override
  public BaseResponseDTO<WorkspaceResponseDTO> get(String email) {
    Workspace workspace = workspaceRepository.findByEmail(email).orElseThrow(() -> new InvalidEntityIdException("This user doesn't have an workspace!"));
    WorkspaceResponseDTO response = this.dtoMapper(workspace);

    return new BaseResponseDTO<>(response, HttpStatus.OK);
  }


  // convert dto to model
  private Workspace modelMapper(WorkspaceRequestDTO workspaceRequestDTO) {
    Workspace workspace = new Workspace();
    workspace.setEmail(workspaceRequestDTO.getEmail());
    workspace.setBoards(new ArrayList<>());
    return workspace;
  }

  // convert to dto for response
  private WorkspaceResponseDTO dtoMapper(Workspace workspace) {
    WorkspaceResponseDTO response = new WorkspaceResponseDTO();
    response.setId(workspace.getId());
    response.setEmail(workspace.getEmail());
    response.setCreatedDate(workspace.getCreatedDate());

    List<BoardResponseDTO> boards = new ArrayList<>();
    for (String id : workspace.getBoards()) {
      Board board = boardRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Invalid board id!"));
      BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
      boardResponseDTO.setTitle(board.getTitle());
      boardResponseDTO.setDescription(board.getDescription());
      boardResponseDTO.setCreatedDate(board.getCreatedDate());

      boards.add(boardResponseDTO);
    }

    response.setBoards(boards);
    return response;
  }
}
