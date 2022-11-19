package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String description;
    private LocalDateTime date;
    private double amount;
    private TransactionType type;
    private Double accountBalance;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(String description, LocalDateTime date, double amount, TransactionType type, Account account) {
        this.description = description;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.account = account;
        this.accountBalance=transactionBalance();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public String getDescription() {
        //String newDescription="";
        if (type==TransactionType.CREDIT){
            return description+"-Credit";
        }
        else {
            return description+"-Debit";
        }

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

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public Double transactionBalance(){
        if(account.getBalance()>=amount){
            return account.getBalance()+amount;
        }
        else {
            return 0.0;
        }
    }
}
