package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    //consulta select * client where lastName...
    Optional<Client> findByLastName(String lastName);
    Optional<Client> findByEmail(String email);
    /*ClientRepository:
        -Buscar una lista de clientes por nombre
        -Buscar un cliente por Nombre y Email
        -Buscar una lista de clientes por apellido*/
    List<Client> findByFirstName(String firstName);
    Optional<Client> findByFirstNameAndEmail(String firstName, String email);
    List<Client> findByLastNameOrderByLastNameAsc(String lastName);
}
