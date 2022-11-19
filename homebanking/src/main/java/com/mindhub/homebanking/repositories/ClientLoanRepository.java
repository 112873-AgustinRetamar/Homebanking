package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    /*ClientLoanRepository:
        -Buscar una lista de ClientLoan por cliente
        -Buscar una lista de ClientLoan que sean mayores a x monto pasado por parametro
        -Buscar una lista de ClientLoan por cliente que  en cual sus balances sean menores a x monto pasado por parametro*/
    List<ClientLoan> findByClient(Client client);
    List<ClientLoan> findByAmountLessThan(double amount);
    List<ClientLoan> findByClient_idAndAmountLessThan(Long id,double amount);
}
