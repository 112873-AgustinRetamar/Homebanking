package com.mindhub.homebanking.controllers;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    //mapeado de clientes por dto
    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        //java stream (para listas)
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    //mapeado de cliente buscado por id (JAVA 8)
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }
    //mapea clientes x apellido
    @GetMapping("/clients/lastName/{lastName}")
    public ClientDTO getByClientLastName(@PathVariable String lastName){
        return clientRepository.findByLastName(lastName).map(ClientDTO::new).orElse(null);
    }
    //mapea clientes x nombre
    @GetMapping("/clients/firstName/{firstName}")
    List <ClientDTO> findByClientFirstName(@PathVariable String firstName){
        return clientRepository.findByFirstName(firstName).stream().map(ClientDTO::new).collect(Collectors.toList());
    }
    //mapea clientes x email
    @GetMapping("/clients/email/{email}")
    public ClientDTO getByEmail(@PathVariable String email){
        return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
    }
    //post nuevo client en el Sing Up
    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                               @RequestParam String email, @RequestParam String password){

        //valida que los campos no sean nulos
        if (firstName.isEmpty() ||  lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        //valida que mail no sea igual a uno ya existente
        if (clientRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("UserName already exists", HttpStatus.FORBIDDEN);
        }
        //nuevo cliente
        Client client = clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password) ));
        //Account para el nuevo cliente
        Account account= accountRepository.save(new Account(8,LocalDateTime.now(),0,client, AccountType.CURRENT));

        //Si todó salió bien. Retorno un estado CREATED que es correcto
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).map(ClientDTO::new).orElse(null);
    }

}
