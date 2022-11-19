package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /*TransactionRepository:
        -Buscar una lista de transacciones entre dos fechas pasadas por parametro
        -Buscar una lista de transacciones en las cuales el monto se mayor a x monto pasado como primer parametro,
              y menor a x monto  pasado como segundo parametro
        -Buscar una lista de transacciones por type*/
    List<Transaction> findByDateBetween(LocalDateTime from, LocalDateTime to);
    List<Transaction> findByAmountBetween(double greaterThan,double lessThan);
    List<Transaction> findByType(TransactionType type);
}
