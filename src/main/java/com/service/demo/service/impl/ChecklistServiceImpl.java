package com.service.demo.service.impl;

import com.service.demo.dto.request.ChecklistRequestDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.exception.InvalidIndexException;
import com.service.demo.model.Card;
import com.service.demo.model.Checklist;
import com.service.demo.model.Task;
import com.service.demo.model.enums.TaskStatus;
import com.service.demo.repository.CardRepository;
import com.service.demo.repository.ChecklistRepository;
import com.service.demo.service.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChecklistServiceImpl implements ChecklistService {
  @Autowired
  private ChecklistRepository checklistRepository;

  @Autowired
  private CardRepository cardRepository;

  /**
   * Create new checklist with empty body
   *
   * @param checklistRequestDTO
   * @return
   */
  @Override
  public BaseResponseDTO<String> create(ChecklistRequestDTO checklistRequestDTO) {
    Checklist checklist = this.modelMapper(checklistRequestDTO);
    String cardId = checklist.getCardId();
    Card card = cardRepository.findById(cardId).orElseThrow(() -> new InvalidEntityIdException("Card ID::" + cardId + " is invalid!"));
    // save checklist id into cards
    String checklistId = checklistRepository.save(checklist).getId();
    card.getChecklists().add(checklistId);

    cardRepository.save(card);
    return new BaseResponseDTO<>("Create checklist succesfully", HttpStatus.CREATED);
  }


  /**
   * This method only update title of only 1 checklist
   *
   * @param checklistRequestDTO
   * @return
   */
  @Override
  public BaseResponseDTO<String> updateTitle(ChecklistRequestDTO checklistRequestDTO) {
    Checklist checklist = checklistRepository.findById(checklistRequestDTO.getId()).orElseThrow(() -> new InvalidEntityIdException("Card ID is invalid!"));
    checklist.setTitle(checklistRequestDTO.getTitle());
    checklistRepository.save(checklist);
    return new BaseResponseDTO<>("Update checklist's title successfully", HttpStatus.OK);
  }

  /**
   * Delete 1 checklist
   *
   * @param id
   * @return
   */
  @Override
  public BaseResponseDTO<String> delete(String id) {
    Checklist checklist = checklistRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Checklist ID is invalid!"));
    Card card = cardRepository.findById(checklist.getCardId()).orElseThrow(() -> new InvalidEntityIdException("Card ID is invalid!"));
    card.getChecklists().remove(id);
    cardRepository.save(card);
    checklistRepository.deleteById(id);
    return new BaseResponseDTO<>("Delete checklist succesfully", HttpStatus.OK);
  }

  /**
   * Add task to checklist
   *
   * @param id
   * @param title
   * @return
   */
  @Override
  public BaseResponseDTO<String> addTask(String id, String title) {
    Checklist checklist = checklistRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Checklist ID is invalid!"));
    Task task = new Task(title, TaskStatus.IN_PROGRESS);
    List<Task> tasks = checklist.getTasks();
    tasks.add(task);
    checklist.setProgress(calculateProgress(tasks));
    checklistRepository.save(checklist);
    return new BaseResponseDTO<>("Add task to checklist successfully", HttpStatus.OK);
  }

  /**
   * Delete by index in the tasks array
   *
   * @param id
   * @param index
   * @return
   */
  @Override
  public BaseResponseDTO<String> deleteTask(String id, int index) {
    Checklist checklist = checklistRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Checklist ID is invalid!"));
    List<Task> tasks = checklist.getTasks();
    if (index >= tasks.size()) {
      throw new InvalidIndexException("Index is too large for tasks' size");
    }
    tasks.remove(index);
    checklist.setProgress(calculateProgress(tasks));
    checklistRepository.save(checklist);
    return new BaseResponseDTO<>("Delete task successfully", HttpStatus.OK);
  }

  /**
   * Check complete 1 task in checklist
   * Can both check and uncheck
   *
   * @param id
   * @param index
   * @return
   */
  @Override
  public BaseResponseDTO<String> checkTask(String id, int index) {
    Checklist checklist = checklistRepository.findById(id).orElseThrow(() -> new InvalidEntityIdException("Checklist ID is invalid!"));
    List<Task> tasks = checklist.getTasks();
    if (index >= tasks.size()) {
      throw new InvalidIndexException("Index is too large for tasks' size");
    }
    Task task = tasks.get(index);
    if (task.getStatus() == TaskStatus.DONE) {
      task.setStatus(TaskStatus.IN_PROGRESS);
    } else {
      task.setStatus(TaskStatus.DONE);
    }
    checklist.setProgress(calculateProgress(tasks));
    checklistRepository.save(checklist);
    return new BaseResponseDTO<>("Change task's status successfully", HttpStatus.OK);
  }

  /**
   * convert to model
   * Only use to create new object
   */
  private Checklist modelMapper(ChecklistRequestDTO checklistRequestDTO) {
    Checklist checklist = new Checklist();
    checklist.setTitle(checklistRequestDTO.getTitle());
    checklist.setTasks(new ArrayList<>());
    checklist.setCardId(checklistRequestDTO.getCardId());
    checklist.setProgress((double) 0);
    return checklist;
  }

  /**
   * Calculate percentage of completion for 1 checklist
   *
   * @param tasks
   * @return
   */
  private double calculateProgress(List<Task> tasks) {
    if (tasks.size() == 0) {
      return 0;
    }
    int countDone = 0;
    for (Task task : tasks) {
      if (task.getStatus() == TaskStatus.DONE) {
        countDone++;
      }
    }
    return 100 * ((double) countDone / tasks.size());
  }
}
