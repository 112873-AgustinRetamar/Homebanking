package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;
    //devuelve la lista de tarjetas por id de cada cliente, si existe.
    @GetMapping("/clients/{id}/cards")
    public List<CardDTO> getCards(@PathVariable Long id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.map(client -> client.getCards().stream().map(CardDTO::new).collect(Collectors.toList())).orElseGet(ArrayList::new);
        //return clientRepository.findById(id).get().getCards().stream().map(CardDTO::new).collect(Collectors.toList());
    }
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType , @RequestParam CardColor cardColor,
                                             Authentication authentication){
        Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());
        //Ojo! El cliente no puede tener mas de tres Cards del mismo cardType
        //Si tiene mas de tres
        //return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN
        if (Objects.isNull(cardType)|| Objects.isNull(cardColor) ){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (client.isPresent()){
            if (cardType==CardType.CREDIT && client.get().creditCardAmount()>=3){
                return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            }

            if (cardType==CardType.DEBIT &&  client.get().debitCardAmount()>=3){
                return new ResponseEntity<>("You already have 3 " + cardType + " cards", HttpStatus.FORBIDDEN);
            }
            Card card = new Card(cardType, cardColor, client.get());
            try {
                cardRepository.save(card);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (Exception ex){
                ex.printStackTrace();
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("User does not exist", HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id) {
        cardRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
