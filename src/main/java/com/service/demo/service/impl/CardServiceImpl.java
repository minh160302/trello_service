package com.service.demo.service.impl;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.CardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Card;
import com.service.demo.model.Catalog;
import com.service.demo.model.Checklist;
import com.service.demo.repository.CardRepository;
import com.service.demo.repository.CatalogRepository;
import com.service.demo.repository.ChecklistRepository;
import com.service.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private CatalogRepository catalogRepository;

  @Autowired
  private ChecklistRepository checklistRepository;

  /**
   * Get information of one card
   * RECOMMEND: call this after change card's database
   *
   * @param id String
   * @return BaseResponseDTO
   */
  @Override
  public BaseResponseDTO<CardResponseDTO> get(String id) {
    Card card = cardRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Card ID::" + id + " is invalid!"));

    return new BaseResponseDTO<>(this.dtoMapper(card), HttpStatus.OK);
  }

  @Override
  public BaseResponseDTO<String> create(CardRequestDTO cardRequestDTO) {
    Card card = this.modelMapper(cardRequestDTO);
    String catalogId = card.getCatalogId();
    // valid catalog id
    Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() -> new InvalidEntityIdException("Catalog ID::" + catalogId + " is invalid!"));
    // add id
    String id = cardRepository.save(card).getId();
    catalog.getCards().add(id);
    catalogRepository.save(catalog);
    return new BaseResponseDTO<>("Create card successfully", HttpStatus.CREATED);
  }

  /**
   * @param cardRequestDTO only update metadata
   * @return BaseResponseDTO
   */
  @Override
  public BaseResponseDTO<CardResponseDTO> update(CardRequestDTO cardRequestDTO) {
    Card card = cardRepository.findById(cardRequestDTO.getId()).orElseThrow(() -> new InvalidEntityIdException("Invalid card id!"));
    card.setTitle(cardRequestDTO.getTitle());
    card.setAttachments(cardRequestDTO.getAttachments());
    card.setDescription(cardRequestDTO.getDescription());
    card.setChecklists(cardRequestDTO.getChecklists());
    cardRepository.save(card);

    return new BaseResponseDTO<>(this.dtoMapper(card), HttpStatus.OK);
  }

  @Override
  public BaseResponseDTO<String> delete(String id) {
    Card card = cardRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Invalid card id!"));
    String catalogId = card.getCatalogId();
    Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(() -> new InvalidEntityIdException("Invalid catalog id!"));
    catalog.getCards().remove(id);

    for (String checklistId : card.getChecklists()) {
      checklistRepository.deleteById(checklistId);
    }

    catalogRepository.save(catalog);
    cardRepository.deleteById(id);
    return new BaseResponseDTO<>("Delete card successfully", HttpStatus.OK);
  }

  /**
   * Convert Card to CardResponseDTO
   *
   * @param card Card
   * @return CardResponseDTO
   */
  private CardResponseDTO dtoMapper(Card card) {
    CardResponseDTO response = new CardResponseDTO();
    response.setId(card.getId());
    response.setDescription(card.getDescription());
    response.setMembers(card.getMembers());
    response.setCatalogId(card.getCatalogId());
    response.setAttachments(card.getAttachments());
    response.setTitle(card.getTitle());

    List<Checklist> checklists = new ArrayList<>();
    List<String> checklistIds = card.getChecklists();
    for (String id : checklistIds) {
      Checklist checklist = checklistRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Checklist ID is invalid!"));
      checklists.add(checklist);
    }
    response.setChecklists(checklists);
    return response;
  }

  /**
   * Only use to create new object
   *
   * @param cardRequestDTO CardRequestDTO
   * @return Card
   */
  private Card modelMapper(CardRequestDTO cardRequestDTO) {
    Card card = new Card();
    card.setTitle(cardRequestDTO.getTitle());
    card.setDescription(cardRequestDTO.getDescription());
    card.setCatalogId(cardRequestDTO.getCatalogId());
    card.setMembers(new ArrayList<>());
    card.setAttachments(new ArrayList<>());
    card.setChecklists(new ArrayList<>());
    return card;
  }
}
