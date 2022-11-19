package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    /*AccountRepository:
         -Buscar una lista de cuentas en el cual su balance se mayor a x monto pasado por parametro
         -Buscar una lista de cuentas por en la cual sue fecha se menor a la que le pasemos por parametro
         -Buscar una cuenta por Numero de cuenta*/
    List<Account> findByBalanceGreaterThan(double balance);
    List<Account> findByCreationDateBefore(LocalDateTime creationDate);
    Optional<Account> findByNumber(String number);

}
