package com.lafryhi.yassine.digital.banking.dtos;


import lombok.Data;
import com.lafryhi.yassine.digital.banking.enums.AccountStatus;

import java.util.Date;

@Data
public class BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
    private String type ;
    private double interestRate ;

}
