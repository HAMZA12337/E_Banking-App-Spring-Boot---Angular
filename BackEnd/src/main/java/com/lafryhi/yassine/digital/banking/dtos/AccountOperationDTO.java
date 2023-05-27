package com.lafryhi.yassine.digital.banking.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.lafryhi.yassine.digital.banking.enums.OperationType;

import java.util.Date;


@Data

@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationDTO {
    private Long id ;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    private String accountId;


}
