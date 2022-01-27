package com.service.demo.controller;

import com.service.demo.dto.request.CardRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {
  @Autowired
  private CardService cardService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody CardRequestDTO cardRequestDTO) {
    return cardService.create(cardRequestDTO);
  }
}
