package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private String description;
    private LocalDateTime date;
    private double amount;
    private TransactionType type;
    private Double accountBalance;

    public TransactionDTO(Transaction transaction){
        this.id= transaction.getId();
        this.description=transaction.getDescription();
        this.date=transaction.getDate();
        this.amount= transaction.getAmount();
        this.type=transaction.getType();
        this.accountBalance= transaction.getAccountBalance();
    }
    public TransactionDTO(String description, LocalDateTime date, double amount, TransactionType type) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
