package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {

    //Este DTO debe tener id del préstamo, monto, cuotas y número de cuenta de destino.
    private Long loanId;
    private int payments;
    private double amount;
    private String toAccountNumber;

    public LoanApplicationDTO(Long loanId, int payments, double amount, String toAccountNumber) {
        this.loanId = loanId;
        this.payments = payments;
        this.amount = amount;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
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

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

}
