package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.CardDTO;
import com.project.onlinebanking.dto.CardInfoDTO;
import com.project.onlinebanking.dto.CreateCardDTO;
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

//    @GetMapping("/search")
//    public ResponseEntity<List<Card>> getCard(Principal principal) {
//        User user = userService.getUserByUsername(principal.getName());
//        List<Card> cards = cardService.getCards(user);
//        return ResponseEntity.ok(cards);
//    }

    @GetMapping("/return")
    public ResponseEntity<List<CardDTO>> returnCards(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());

        List<CardDTO> cardsInfo = cardService.getCards(user);

        return ResponseEntity.ok(cardsInfo);
    }

    @GetMapping("/return/{cardNumber}")
    public ResponseEntity<CardInfoDTO> returnCardByNumber(@PathVariable String cardNumber) {
        CardInfoDTO cardInfo = cardService.getCardInfoByCardNumber(cardNumber);
        return ResponseEntity.ok(cardInfo);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCard(@RequestBody CreateCardDTO createCardDTO, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        cardService.createCard(user, createCardDTO.getPin());
        return ResponseEntity.ok("Card created");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok("Card deleted");
    }

    @DeleteMapping("/deleteByNumber/{cardNumber}")
    public ResponseEntity<String> deleteCardByNumber(@PathVariable String cardNumber) {
        cardService.deleteCardByNumber(cardNumber);
        return ResponseEntity.ok("Card deleted");
    }
}
