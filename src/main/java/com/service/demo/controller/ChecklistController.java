package com.service.demo.controller;

import com.service.demo.dto.request.ChecklistRequestDTO;
import com.service.demo.dto.request.util.TaskRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@RestController
@RequestMapping("/checklist")
public class ChecklistController {
  @Autowired
  private ChecklistService checklistService;

  @PostMapping
  public BaseResponseDTO<String> create(@RequestBody ChecklistRequestDTO checklistRequestDTO) {
    return checklistService.create(checklistRequestDTO);
  }

  @PutMapping("/title")
  public BaseResponseDTO<String> updateTitle(@RequestBody ChecklistRequestDTO checklistRequestDTO) {
    return checklistService.updateTitle(checklistRequestDTO);
  }

  @DeleteMapping("/{id}")
  public BaseResponseDTO<String> delete(@PathVariable String id) {
    return checklistService.delete(id);
  }

  @PostMapping("/{id}")
  public BaseResponseDTO<String> addTask(@PathVariable String id, @RequestBody TaskRequestDTO task) {
    return checklistService.addTask(id, task.getTitle());
  }

  @DeleteMapping("/{id}/index/{index}")
  public BaseResponseDTO<String> deleteTask(@PathVariable String id, @PathVariable String index) {
    return checklistService.deleteTask(id, Integer.parseInt(index));
  }

  @PutMapping("/{id}/index/{index}")
  public BaseResponseDTO<String> checkTask(@PathVariable String id, @PathVariable String index) {
    return checklistService.checkTask(id, Integer.parseInt(index));
  }
}
