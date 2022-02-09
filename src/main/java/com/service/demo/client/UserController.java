package com.service.demo.client;

import com.service.demo.dto.request.util.AssignCardRequestDTO;
import com.service.demo.dto.response.client.ResponseClientDTO;
import com.service.demo.dto.response.common.BaseResponseDTO;
import com.service.demo.exception.InvalidEntityIdException;
import com.service.demo.model.Card;
import com.service.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private UserClient userClient;

  RestTemplate restTemplate;

//  public UserController(RestTemplate restTemplate) {
//    this.restTemplate = restTemplate;
//  }

//  @GetMapping
//  public List<?> getUsers() {
//    HttpHeaders httpHeaders = new HttpHeaders();
//    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    HttpEntity<User> entity = new HttpEntity<>(httpHeaders);
//    return restTemplate.exchange("http://localhost:8300/api/auth/users",
//            HttpMethod.GET,
//            entity,
//            List.class).getBody();
//  }

//  @GetMapping("/{email}")
//  public ResponseClientDTO<?> getUserByEmail(@PathVariable String email) {
//    HttpHeaders httpHeaders = new HttpHeaders();
//    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    HttpEntity<User> entity = new HttpEntity<>(httpHeaders);
//    return restTemplate.exchange("http://localhost:8300/api/auth/users/" + email,
//            HttpMethod.GET, entity,
//            ResponseClientDTO.class).getBody();
//  }

  /**
   * TODO:
   * 1. add user to task
   * 2. remove user from task
   *
   * @return
   */
//  public ResponseClientDTO<User> getByEmail(@PathVariable String email) {
//    HttpHeaders httpHeaders = new HttpHeaders();
//    httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//    HttpEntity<User> entity = new HttpEntity<>(httpHeaders);
//    ParameterizedTypeReference<ResponseClientDTO<User>> userTypeRef = new ParameterizedTypeReference<ResponseClientDTO<User>>() {
//    };
//
//    return restTemplate.exchange("http://localhost:8300/api/auth/users/" + email,
//            HttpMethod.GET, entity,
//            userTypeRef).getBody();
//  }


  @PostMapping("/assign")
  public BaseResponseDTO<String> assignCard(@RequestBody AssignCardRequestDTO assignCardRequestDTO) {
    String cardId = assignCardRequestDTO.getCardId();
    String email = assignCardRequestDTO.getEmail();
    ResponseClientDTO<User> response = userClient.getUserByEmail(email);

    if (response.getStatus() == 200) {
      User user = response.getData();
      Card card = cardRepository.findById(cardId).orElseThrow(() -> new InvalidEntityIdException("Invalid card id!"));
      // add email to members
      List<String> members = card.getMembers();
      if (!members.contains(user.getEmail())) {
        card.getMembers().add(user.getEmail());
        cardRepository.save(card);
        return new BaseResponseDTO(response.getData(), HttpStatus.OK);
      } else {
        return new BaseResponseDTO("This member is already assigned to this card!", HttpStatus.OK);
      }
    } else {
      return new BaseResponseDTO<>("Assign user failed", HttpStatus.OK);
    }
  }
}
