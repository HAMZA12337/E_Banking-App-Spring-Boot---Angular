package com.digital.banking.app.adapters.models;


import java.util.Date;

import com.digital.banking.app.adapters.enums.AccountStatus;

public class BankAccount {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private Customer customerDTO;
    private double overDraft;
    private String type;
    private double interestRate;


    public BankAccount() {
    }

    public BankAccount(String id, double balance, Date createdAt, AccountStatus status, Customer customerDTO, double overDraft, String type, double interestRate) {
        this.id = id;
        this.balance = balance;
        this.createdAt = createdAt;
        this.status = status;
        this.customerDTO = customerDTO;
        this.overDraft = overDraft;
        this.type = type;
        this.interestRate = interestRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Customer getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(Customer customerDTO) {
        this.customerDTO = customerDTO;
    }

    public double getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(double overDraft) {
        this.overDraft = overDraft;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
