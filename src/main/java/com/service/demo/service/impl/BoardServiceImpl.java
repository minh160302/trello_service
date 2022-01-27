package com.service.demo.service.impl;

import com.service.demo.dto.request.BoardRequestDTO;
import com.service.demo.dto.response.BoardResponseDTO;
import com.service.demo.dto.response.CardResponseDTO;
import com.service.demo.dto.response.CatalogResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Board;
import com.service.demo.model.Card;
import com.service.demo.model.Catalog;
import com.service.demo.model.Workspace;
import com.service.demo.repository.BoardRepository;
import com.service.demo.repository.CardRepository;
import com.service.demo.repository.CatalogRepository;
import com.service.demo.repository.WorkspaceRepository;
import com.service.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
  @Autowired
  private BoardRepository boardRepository;

  @Autowired
  private WorkspaceRepository workspaceRepository;

  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private CardRepository cardRepository;

  @Override
  public BaseResponseDTO<String> create(BoardRequestDTO boardRequestDTO) {
    Board board = this.modelMapper(boardRequestDTO);
    String workspaceId = board.getWorkspaceId();
    // check valid workspace id
    Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() -> new InvalidEntityIdException("Workspace ID::" + workspaceId + " is invalid!"));
    // add id to list
    String id = boardRepository.save(board).getId();
    workspace.getBoards().add(id);
    workspaceRepository.save(workspace);
    return new BaseResponseDTO<>("Create board successfully", HttpStatus.CREATED);
  }

  @Override
  public BaseResponseDTO<BoardResponseDTO> getById(String id) {
    Board board = boardRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Invalid board id!"));
    BoardResponseDTO boardResponseDTO = this.dtoMapper(board);
    return new BaseResponseDTO<>(boardResponseDTO, HttpStatus.OK);
  }

  private Board modelMapper(BoardRequestDTO boardRequestDTO) {
    Board board = new Board();
    board.setDescription(boardRequestDTO.getDescription());
    // TODO: set authorization
    board.setTitle(boardRequestDTO.getTitle());
    board.setCatalogs(new ArrayList<>());
    board.setUpdatedDate(Instant.now());
    board.setWorkspaceId(boardRequestDTO.getWorkspaceId());

    return board;
  }


  private BoardResponseDTO dtoMapper(Board board) {
    BoardResponseDTO response = new BoardResponseDTO();
    response.setId(board.getId());
    response.setTitle(board.getTitle());
    response.setDescription(board.getDescription());
    response.setUpdatedDate(board.getUpdatedDate());

    List<CatalogResponseDTO> catalogs = new ArrayList<>();
    for (String id : board.getCatalogs()) {
      Catalog catalog = catalogRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Invalid catalog id!"));
      CatalogResponseDTO catalogResponseDTO = new CatalogResponseDTO();
      catalogResponseDTO.setId(catalog.getId());
      catalogResponseDTO.setTitle(catalog.getTitle());
      catalogResponseDTO.setCreatedDate(catalog.getCreatedDate());
      catalogResponseDTO.setUpdatedDate(catalog.getUpdatedDate());

      List<CardResponseDTO> cards = new ArrayList<>();
      for (String cardId: catalog.getCards()) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new InvalidEntityIdException("Invalid card id!"));
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setId(card.getId());
        cardResponseDTO.setTitle(card.getTitle());
        cardResponseDTO.setDescription(card.getDescription());
        cardResponseDTO.setAttachment(card.getAttachment());
        cardResponseDTO.setMembers(card.getMembers());

        cards.add(cardResponseDTO);
      }
      catalogResponseDTO.setCards(cards);

      catalogs.add(catalogResponseDTO);
    }

    response.setCatalogs(catalogs);
    return response;
  }
}
