package com.digital.banking.app.adapters.models;

import java.util.List;

public class AccountHistory {

    public  String accountId;
    private double balance ;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    List<AccountOperation> accountOperationDTOList;

    public AccountHistory() {
    }

    public AccountHistory(String accountId, double balance, int currentPage, int totalPages, int pageSize, List<AccountOperation> accountOperationDTOList) {
        this.accountId = accountId;
        this.balance = balance;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageSize = pageSize;
        this.accountOperationDTOList = accountOperationDTOList;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<AccountOperation> getAccountOperationDTOList() {
        return accountOperationDTOList;
    }

    public void setAccountOperationDTOList(List<AccountOperation> accountOperationDTOList) {
        this.accountOperationDTOList = accountOperationDTOList;
    }
}
