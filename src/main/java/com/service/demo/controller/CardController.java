package com.service.demo.controller;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.CardResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {
  @Autowired
  private CardService cardService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody CardRequestDTO cardRequestDTO) {
    return cardService.create(cardRequestDTO);
  }

  @PutMapping
  public BaseResponseDTO<CardResponseDTO> update(@RequestBody CardRequestDTO cardRequestDTO) {
    return cardService.update(cardRequestDTO);
  }

  @DeleteMapping("/{id}")
  public BaseResponseDTO<String> delete(@PathVariable String id) {
    return cardService.delete(id);
  }
}
