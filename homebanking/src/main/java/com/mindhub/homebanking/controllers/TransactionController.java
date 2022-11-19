package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/transaction")
    public List<TransactionDTO> getTransactions(){
        //java stream (para listas) lista todas las transactions x el DTO
        return transactionRepository.findAll().stream().map(TransactionDTO::new).collect(toList());
    }
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam double amount, @RequestParam String description,
                                               @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber){

        //valida que los campos no sean nulos
        if (amount==0 ||  description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        //valida que no sea el mismo numero de cuenta
        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Same account", HttpStatus.FORBIDDEN);
        }
        Optional<Account> from=accountRepository.findByNumber(fromAccountNumber);
        Optional<Account> to=accountRepository.findByNumber(toAccountNumber);
        if (!from.isPresent()){
            return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);
        }
        Optional<Client> client = clientRepository.findByEmail(from.get().getClient().getemail());
        if (!client.isPresent()){
            return new ResponseEntity<>("Unauthorized Client", HttpStatus.FORBIDDEN);
        }
        if (!to.isPresent()){
            return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);
        }
        if (from.get().getBalance()<amount){
            return new ResponseEntity<>("Account does not have required balance", HttpStatus.FORBIDDEN);
        }

        //Si las validaciones estan bien se crean 2 cuentas:
        Transaction transaction1=new Transaction(description+"-"+to.get().getNumber(),LocalDateTime.now(),-(amount), TransactionType.DEBIT,from.get());
        transactionRepository.save(transaction1);
        Transaction	transaction2=new Transaction(description+"-"+from.get().getNumber(),LocalDateTime.now(),amount, TransactionType.CREDIT,to.get());
        transactionRepository.save(transaction2);

        //Actualiza los balances en las cuentas:
        from.get().setBalance(from.get().getBalance()-amount);
        accountRepository.save(from.get());
        to.get().setBalance(to.get().getBalance()+amount);
        accountRepository.save(to.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/transactions/dateBetween/{date}/{date2}")
    public List<TransactionDTO> getTransactionByDateBetween(@PathVariable String date, @PathVariable String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (date.isEmpty() || date2.isEmpty()) {
            return null;
        } else {
            return transactionRepository.findByDateBetween((LocalDateTime.parse(date, formatter)), (LocalDateTime.parse(date2, formatter))).stream().map(TransactionDTO::new).collect(toList());
        }
    }
}
