package com.braimi.hamza.digital.banking;


import com.braimi.hamza.digital.banking.dtos.BankAccountDTO;
import com.braimi.hamza.digital.banking.dtos.CustomerDTO;
import com.braimi.hamza.digital.banking.dtos.SavingBankAccountDTO;
import com.braimi.hamza.digital.banking.enums.AccountStatus;
import com.braimi.hamza.digital.banking.enums.OperationType;
import com.braimi.hamza.digital.banking.entities.AccountOperation;
import com.braimi.hamza.digital.banking.entities.CurrentAccount;
import com.braimi.hamza.digital.banking.entities.SavingAccount;
import com.braimi.hamza.digital.banking.exceptions.CustomerNotFoundException;
import com.braimi.hamza.digital.banking.repositories.AccountOperationRepository;
import com.braimi.hamza.digital.banking.repositories.BankAccountRepository;
import com.braimi.hamza.digital.banking.repositories.CustomerRepository;
import com.braimi.hamza.digital.banking.security.entities.AppRole;
import com.braimi.hamza.digital.banking.security.entities.AppUser;
import com.braimi.hamza.digital.banking.security.services.AccountService;
import com.braimi.hamza.digital.banking.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, AccountService accountService) {
        return args -> {

            accountService.addNewRole(new AppRole(null, "ADMIN"));
            accountService.addNewRole(new AppRole(null, "CUSTOMER"));

            accountService.addNewUser(new AppUser(null, "HAMZA", "1234", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "admin", "1234", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "Hafsa", "1234", new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "Moussa", "1234", new ArrayList<>()));

            accountService.addRoleToUser("Yassine", "CUSTOMER");
            accountService.addRoleToUser("admin", "ADMIN");
            accountService.addRoleToUser("Hafsa", "CUSTOMER");
            accountService.addRoleToUser("Moussa", "CUSTOMER");

            Stream.of("Yassine", "Hafsa", "Moussa").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@hotmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers(1).forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.getBankAccountList(1).getBankAccountDTOS();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if (bankAccount instanceof SavingBankAccountDTO) {
                        accountId = bankAccount.getId();
                    } else {
                        accountId = bankAccount.getId();
                    }
                    bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");
                    bankAccountService.debit(accountId, 1000 + Math.random() * 9000, "Debit");
                }
            }
        };
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random() * 90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }

            });
        };

    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
