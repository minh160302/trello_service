package com.service.demo.service.impl;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.CardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Card;
import com.service.demo.model.Catalog;
import com.service.demo.repository.CardRepository;
import com.service.demo.repository.CatalogRepository;
import com.service.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CardServiceImpl implements CardService {
  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private CatalogRepository catalogRepository;

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
   *
   * @param cardRequestDTO only update metadata
   * @return
   */
  @Override
  public BaseResponseDTO<CardResponseDTO> update(CardRequestDTO cardRequestDTO) {
    Card card = cardRepository.findById(cardRequestDTO.getId()).orElseThrow(() -> new InvalidEntityIdException("Invalid card id!"));
    card.setTitle(cardRequestDTO.getTitle());
    card.setAttachments(cardRequestDTO.getAttachments());
    card.setDescription(cardRequestDTO.getDescription());

    return new BaseResponseDTO(cardRepository.save(card), HttpStatus.OK);
  }

  @Override
  public BaseResponseDTO<String> delete(String id) {
    Card card = cardRepository.findById(id).orElseThrow(()-> new InvalidEntityIdException("Invalid card id!"));
    String catalogId = card.getCatalogId();
    Catalog catalog = catalogRepository.findById(catalogId).orElseThrow(()-> new InvalidEntityIdException("Invalid catalog id!"));
    catalog.getCards().remove(id);

    catalogRepository.save(catalog);
    cardRepository.deleteById(id);
    return new BaseResponseDTO<>("Delete card successfully", HttpStatus.OK);
  }

  private Card modelMapper(CardRequestDTO cardRequestDTO) {
    Card card = new Card();
    card.setTitle(cardRequestDTO.getTitle());
    card.setDescription(cardRequestDTO.getDescription());
    card.setCatalogId(cardRequestDTO.getCatalogId());
    card.setMembers(new ArrayList<>());
    card.setAttachments(new ArrayList<>());
    return card;
  }
}
