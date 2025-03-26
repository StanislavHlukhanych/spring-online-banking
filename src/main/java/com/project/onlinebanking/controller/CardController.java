package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.CreateCardDTO;
import com.project.onlinebanking.entity.Card;
import com.project.onlinebanking.entity.User;
import com.project.onlinebanking.service.CardService;
import com.project.onlinebanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Card>> getCard(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Card> cards = cardService.getCards(user);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/create")
    public ResponseEntity<Card> createCard(@RequestBody CreateCardDTO createCardDTO, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Card card = cardService.createCard(user, createCardDTO.getPin());
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Card deleted");
    }
}
