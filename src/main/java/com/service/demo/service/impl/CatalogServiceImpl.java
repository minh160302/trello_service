package com.service.demo.service.impl;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.response.BoardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Board;
import com.service.demo.model.Catalog;
import com.service.demo.repository.BoardRepository;
import com.service.demo.repository.CatalogRepository;
import com.service.demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CatalogServiceImpl implements CatalogService {
  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private BoardRepository boardRepository;

  @Override
  public BaseResponseDTO<String> create(CatalogRequestDTO catalogRequestDTO) {
    Catalog catalog = this.modelMapper(catalogRequestDTO);
    String boardId = catalog.getBoardId();
    Board board = boardRepository.findById(boardId).orElseThrow(() -> new InvalidEntityIdException("Board ID::" + boardId + " is invalid!"));
    // add id to list cards
    String id = catalogRepository.save(catalog).getId();
    board.getCatalogs().add(id);
    boardRepository.save(board);
    return new BaseResponseDTO<>("Create catalog successfully", HttpStatus.CREATED);
  }

  // convert to model
  private Catalog modelMapper(CatalogRequestDTO catalogRequestDTO) {
    Catalog catalog = new Catalog();
    catalog.setTitle(catalogRequestDTO.getTitle());
    catalog.setBoardId(catalogRequestDTO.getBoardId());
    catalog.setCards(new ArrayList<>());

    return catalog;
  }
}
