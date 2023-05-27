package com.lafryhi.yassine.digital.banking.repositories;

import com.lafryhi.yassine.digital.banking.entities.BankAccount;
import com.lafryhi.yassine.digital.banking.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    Page<BankAccount> findAll(Pageable pageable);
    List<BankAccount> findByCustomer(Customer customer);
}
