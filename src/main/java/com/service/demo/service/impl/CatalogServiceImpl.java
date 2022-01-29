package com.service.demo.service.impl;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.request.util.MoveCardRequestDTO;
import com.service.demo.dto.response.CatalogResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.exception.InvalidIndexException;
import com.service.demo.model.Board;
import com.service.demo.model.Catalog;
import com.service.demo.repository.BoardRepository;
import com.service.demo.repository.CatalogRepository;
import com.service.demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

  /**
   * @param catalogRequestDTO only update catalog title
   * @return
   */
  @Override
  public BaseResponseDTO<CatalogResponseDTO> update(CatalogRequestDTO catalogRequestDTO) {
    Catalog catalog = catalogRepository.findById(catalogRequestDTO.getId()).orElseThrow(() -> new InvalidEntityIdException("Invalid catalog id!"));
    catalog.setTitle(catalogRequestDTO.getTitle());
    catalog.setUpdatedDate(Instant.now());
    return new BaseResponseDTO(catalogRepository.save(catalog), HttpStatus.OK);
  }

  /**
   * @param id also update list catalogs in board
   * @return
   */
  @Override
  public BaseResponseDTO<String> delete(String id) {
    Catalog catalog = catalogRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Invalid catalog id!"));
    String boardId = catalog.getBoardId();
    Board board = boardRepository.findById(boardId).orElseThrow(() -> new InvalidEntityIdException("Invalid board id!"));
    board.getCatalogs().remove(id);

    boardRepository.save(board);
    catalogRepository.deleteById(id);
    return new BaseResponseDTO<>("Delete catalog successfully", HttpStatus.OK);
  }

  /**
   * move cards betweeen catalogs
   *
   * @param moveCardRequestDTO
   * @return success/failure message
   * if move in the same catalog, then swap
   */
  @Override
  public BaseResponseDTO<String> move(MoveCardRequestDTO moveCardRequestDTO) {
    String cardId = moveCardRequestDTO.getCardId();
    String fromCatalogId = moveCardRequestDTO.getFrom();
    String toCatalogId = moveCardRequestDTO.getTo();
    Integer position = moveCardRequestDTO.getPosition();

    Catalog source = catalogRepository.findById(fromCatalogId).orElseThrow(() -> new InvalidEntityIdException("Invalid catalog id!"));
    if (fromCatalogId.equals(toCatalogId)) {
      List<String> cards = source.getCards();
      if (position >= cards.size()) {
        throw new InvalidIndexException("Position index out of bounds!");
      }
      // swap cards
      int originalPosition = cards.indexOf(cardId);
      if (originalPosition == -1) {
        throw new InvalidEntityIdException("Card id is invalid. This card doesn't belong to this catalog");
      }
      String cardAtPosition = cards.get(position);
      cards.set(originalPosition, cardAtPosition);
      cards.set(position, cardId);
      // update in database
      catalogRepository.save(source);
    } else {
      Catalog destination = catalogRepository.findById(toCatalogId).orElseThrow(() -> new InvalidEntityIdException("Invalid destination catalog id!"));
      // check card's existence
      if (!source.getCards().contains(cardId)) {
        throw new InvalidEntityIdException("Card id is invalid. This card doesn't belong to this catalog");
      } else {
        source.getCards().remove(cardId);
        List<String> cards = destination.getCards();
        if (position > cards.size()) {
          throw new InvalidIndexException("Position index out of bounds!");
        } else if (position == cards.size()) {
          cards.add(cardId);
        } else {
          cards.add(position, cardId);
        }
      }
      // update in database
      catalogRepository.save(source);
      catalogRepository.save(destination);
    }

    return new BaseResponseDTO<>("Move card successfully", HttpStatus.OK);
  }

  // convert to model
  private Catalog modelMapper(CatalogRequestDTO catalogRequestDTO) {
    Catalog catalog = new Catalog();
//    catalog.setId(catalogRequestDTO.getId());
    catalog.setTitle(catalogRequestDTO.getTitle());
    catalog.setBoardId(catalogRequestDTO.getBoardId());
    catalog.setCards(new ArrayList<>());

    return catalog;
  }
}
