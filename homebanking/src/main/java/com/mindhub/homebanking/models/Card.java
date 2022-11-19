package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import utils.CardUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private String cvv;
    private String cardHolder;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private CardType type;
    private CardColor color;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card() {
    }
    //contructor ejercicio 28-10
    public Card(LocalDate fromDate, CardType type, CardColor color, Client client) {
        this.fromDate = fromDate;
        this.type = type;
        this.color = color;
        this.client = client;
        this.cardHolder=client.getFirstName()+" "+client.getLastName();
        this.cvv= CardUtils.generate3Random();
    }

    public Card(CardType type, CardColor color, Client client) {
        this.type = type;
        this.color = color;
        this.client = client;
        this.fromDate= LocalDate.now();
        this.thruDate = fromDate.plusYears(5);
        this.cardHolder = client.getFirstName()+" "+client.getLastName();
        this.cvv=CardUtils.generate3Random();
        this.number=CardUtils.generateRandomN(4)+"-"+CardUtils.generateRandomN(4)+"-"+CardUtils.generateRandomN(4)+"-"+CardUtils.generateRandomN(4);
    }

    //constructor suponiendo que se nos pide que el nombre se pase de esta manera y las tarjetas tengan 5 anios de duracion
    public Card(String number, String cvv, LocalDate fromDate, CardType type, CardColor color, Client client) {
        this.number = number;
        this.cvv = cvv;
        this.cardHolder = client.getFirstName()+" "+client.getLastName();
        this.fromDate = fromDate;
        this.thruDate = fromDate.plusYears(5);
        this.type = type;
        this.color = color;
        this.client = client;
    }
    public Card(String number, String cvv, String cardHolder, LocalDate fromDate, LocalDate thruDate, CardType type, CardColor color, Client client) {
        this.number = number;
        this.cvv = cvv;
        this.cardHolder = cardHolder;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.type = type;
        this.color = color;
        this.client = client;
    }

    public Card(CardType type, CardColor color, String number, String cvv, LocalDate now, Client client) {
        this.number = number;
        this.cvv = cvv;
        this.cardHolder = client.getFirstName()+" "+client.getLastName();
        this.type = type;
        this.color = color;
        this.client = client;
    }


    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
