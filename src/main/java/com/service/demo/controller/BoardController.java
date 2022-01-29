package com.service.demo.controller;

import com.service.demo.dto.request.BoardRequestDTO;
import com.service.demo.dto.response.BoardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {
  @Autowired
  private BoardService boardService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody BoardRequestDTO boardRequestDTO) {
    return boardService.create(boardRequestDTO);
  }

  // get a bunch of information
  @GetMapping("/{id}")
  public BaseResponseDTO<BoardResponseDTO> getById(@PathVariable String id) {
    return boardService.getById(id);
  }

  @PutMapping
  public BaseResponseDTO<BoardResponseDTO> update(@RequestBody BoardRequestDTO boardRequestDTO) {
    return boardService.update(boardRequestDTO);
  }

  @DeleteMapping("/{id}")
  public BaseResponseDTO<String> delete(@PathVariable String id) {
    return boardService.delete(id);
  }
}
