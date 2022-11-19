package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        //java stream (para listas)
        return this.accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        //java stream (para listas)
        return this.accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).get().getAccount().stream().map(AccountDTO::new).collect(Collectors.toList());
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        //si existe cliente con ese id crea un nuevo account
        Optional<Client> client = clientRepository.findByEmail(authentication.getName());
        if (client.isPresent() && authentication.getAuthorities().contains(new SimpleGrantedAuthority("CLIENT")
        )) {
            Account account = new Account(8, LocalDateTime.now(), 0.0, client.get(),AccountType.CURRENT);
            if (client.get().getAccount().size() < 3) {
                try {
                    accountRepository.save(account);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("User has more than 3 accounts", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>("User does not exist", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/clients/current/accounts/accountType")
    public ResponseEntity<Object> createAccountWithAccountType(Authentication authentication, @RequestParam AccountType accountType) {
        try {
            //Busco el Client por email, si existe valido que tenga menos de 3 Accounts y creo una nueva
            Optional<Client> client = clientRepository.findByEmail(authentication.getName());
            if(client.isPresent()) {
                if (client.get().getAccount().stream().count() >= 3) {
                    return new ResponseEntity<>("Number of maximum accounts reached", HttpStatus.FORBIDDEN);
                }
                //Creo la nueva Account validando que el number no se repita en la DB
                Account account;
                do {
                    //Le paso el AccountType como CURRENT a la nueva instancia de Account
                    account = new Account(8, LocalDateTime.now(), 0.0, client.get(),accountType);
                } while(accountRepository.findByNumber(account.getNumber()).isPresent());
                accountRepository.save(account);
                return new ResponseEntity<>("New account created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Client not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            return new ResponseEntity<>("Error creating account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //borra cuentas segun id
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Object> deleteAccountAndTransactions(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
