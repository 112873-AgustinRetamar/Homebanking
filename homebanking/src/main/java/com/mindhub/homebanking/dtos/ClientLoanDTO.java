package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private Long id;
    private int payments;
    private double amount;
    private String name;
    private Long loan_id;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.id=clientLoan.getId();
        this.payments= clientLoan.getPayments();
        this.amount= clientLoan.getAmount();
        this.name=clientLoan.getLoan().getName();
        this.loan_id=clientLoan.getLoan().getId();
    }
    public Long getId() {
        return id;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLoan_id() {
        return loan_id;
    }

}
