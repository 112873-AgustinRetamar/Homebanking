package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @GetMapping("/loans")
    public List<LoanDTO> getLoan() {
        return loanRepository.findAll().stream().map(LoanDTO::new).toList();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createClientLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){
        Optional<Account>account=accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
        Optional<Loan> loan=loanRepository.findById(loanApplicationDTO.getLoanId());
        Optional<Client> client=clientRepository.findByEmail(authentication.getName());
       //valida que los campos no sean nulos
        if (loanApplicationDTO.getAmount()<=0 || loanApplicationDTO.getPayments()<=0){
          return new ResponseEntity<>("Amount or payment 0", HttpStatus.FORBIDDEN);
       }

        //valida que exista loan
       if (!loan.isPresent()){
          return new ResponseEntity<>("Loan does not exists", HttpStatus.FORBIDDEN);
       }
       //valida que la cantidad no supere la cantidad maxima del prestamo
       if (loanApplicationDTO.getAmount()>loan.get().getMaxAmount()){
          return new ResponseEntity<>("Exceeds maximum amount possible", HttpStatus.FORBIDDEN);
       }
       //valida que los payments sean correctos
       if (!loan.get().getPayments().contains(loanApplicationDTO.getPayments())){
          return new ResponseEntity<>("Payment not available", HttpStatus.FORBIDDEN);
       }

       //valida que exista la account
       if (!account.isPresent()){
          return new ResponseEntity<>("Account does not exists", HttpStatus.FORBIDDEN);
       }
       //valida que la cuenta es de un cliente autenticado
       if (!authentication.getName().equals(account.get().getClient().getemail())){
          return new ResponseEntity<>("Not an authorized client", HttpStatus.FORBIDDEN);
       }

       //nuevo cliente
       ClientLoan clientLoan1=new ClientLoan(loanApplicationDTO.getPayments(),loanApplicationDTO.getAmount()*1.20, client.get(),loan.get());
       clientLoanRepository.save(clientLoan1);

       //nueva transaction
       Transaction transaction1=new Transaction(loan.get().getName()+"-loan approved", LocalDateTime.now(), loanApplicationDTO.getAmount(), TransactionType.CREDIT,account.get());
       transactionRepository.save(transaction1);

       //actualizar la cuenta de destina con el monto solicitado
       account.get().setBalance(account.get().getBalance()+ loanApplicationDTO.getAmount());
       accountRepository.save(account.get());
       return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
