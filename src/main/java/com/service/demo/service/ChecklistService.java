package com.service.demo.service;

import com.service.demo.dto.request.ChecklistRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;

public interface ChecklistService {
  /**
   * Create new checklist with empty body
   *
   * @param checklistRequestDTO
   * @return
   */
  BaseResponseDTO<String> create(ChecklistRequestDTO checklistRequestDTO);

  /**
   * This method only update title of only 1 checklist
   *
   * @param checklistRequestDTO
   * @return
   */
  BaseResponseDTO<String> updateTitle(ChecklistRequestDTO checklistRequestDTO);

  /**
   * Delete 1 checklist
   *
   * @param id
   * @return
   */
  BaseResponseDTO<String> delete(String id);

  /**
   * Add task to checklist
   *
   * @param id
   * @param title
   * @return
   */
  BaseResponseDTO<String> addTask(String id, String title);

  /**
   * Delete by index in the tasks array
   *
   * @param id
   * @param index
   * @return
   */
  BaseResponseDTO<String> deleteTask(String id, int index);

  /**
   * Check complete 1 task in checklist
   * Can both check and uncheck
   *
   * @param id
   * @param index
   * @return
   */
  BaseResponseDTO<String> checkTask(String id, int index);
}
