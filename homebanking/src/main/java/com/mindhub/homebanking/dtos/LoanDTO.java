package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.time.LocalDateTime;
import java.util.List;

public class LoanDTO {
    private Long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments;
    public LoanDTO(Loan loan){
        this.id= loan.getId();
        this.name= loan.getName();
        this.maxAmount= loan.getMaxAmount();
        this.payments=loan.getPayments();
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
}
