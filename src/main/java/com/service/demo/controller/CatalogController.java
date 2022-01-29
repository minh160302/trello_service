package com.service.demo.controller;

import com.service.demo.dto.request.CatalogRequestDTO;
import com.service.demo.dto.request.util.MoveCardRequestDTO;
import com.service.demo.dto.response.CatalogResponseDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
  @Autowired
  private CatalogService catalogService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody CatalogRequestDTO catalogRequestDTO) {
    return catalogService.create(catalogRequestDTO);
  }

  @PutMapping
  public BaseResponseDTO<CatalogResponseDTO> update(@RequestBody CatalogRequestDTO catalogRequestDTO) {
    return catalogService.update(catalogRequestDTO);
  }

  @PutMapping("/move")
  public BaseResponseDTO<String> move(@RequestBody MoveCardRequestDTO moveCardRequestDTO) {
    return catalogService.move(moveCardRequestDTO);
  }

  @DeleteMapping("/{id}")
  public BaseResponseDTO<String> delete(@PathVariable String id) {
    return catalogService.delete(id);
  }
}
