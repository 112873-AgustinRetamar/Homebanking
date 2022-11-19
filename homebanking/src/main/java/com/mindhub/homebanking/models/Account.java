package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import utils.CardUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Transaction> transactions = new HashSet<>();
    public Account() {
    }
    public Account(LocalDateTime creationDate, double balance, Client client){
        this.number = "VIN"+ CardUtils.generate3Random();
        this.creationDate = creationDate;
        this.balance = balance;
        this.client=client;
    }
    public Account(String number, LocalDateTime creationDate, double balance, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client=client;
    }
    public Account(int digits, LocalDateTime creationDate, double balance, Client client,AccountType accountType) {
        this.number = "VIN"+ CardUtils.generateRandomN(digits);;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client=client;
        this.accountType=accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }


    public void addTransaction(Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}

