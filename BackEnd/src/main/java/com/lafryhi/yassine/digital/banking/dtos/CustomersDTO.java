package com.lafryhi.yassine.digital.banking.dtos;

import lombok.Data;

import java.util.List;

@Data

public class CustomersDTO {

    List<CustomerDTO> customerDTO;
    int totalpage ;
}

