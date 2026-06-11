package com.example.banksystem.Service;

import com.example.banksystem.API.ApiException;
import com.example.banksystem.DTO.IN.AccountIn;
import com.example.banksystem.DTO.Out.AccountOut;
import com.example.banksystem.Model.Account;
import com.example.banksystem.Model.Customer;
import com.example.banksystem.Model.User;
import com.example.banksystem.Repository.AccountRepository;
import com.example.banksystem.Repository.AuthUserRepository;
import com.example.banksystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    public void createAccount(Integer userId, AccountIn dto) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        if (customerRepository.findAccountByAccountNumber(dto.getAccountNumber()) != null) {
            throw new ApiException("Account number already exists");
        }
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setIsActive(false);
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public List<AccountOut> getMyAccounts(Integer userId) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        List<AccountOut> accounts = new ArrayList<>();
        for (Account a : customerRepository.findAccountsByCustomerId(userId)) {
            AccountOut out = new AccountOut();
            out.setId(a.getId());
            out.setAccountNumber(a.getAccountNumber());
            out.setBalance(a.getBalance());
            out.setIsActive(a.getIsActive());
            accounts.add(out);
        }
        return accounts;
    }

    public AccountOut getAccountDetails(Integer userId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Account account = customerRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (!account.getCustomer().getId().equals(userId)) {
            throw new ApiException("This account does not belong to you");
        }

        AccountOut out = new AccountOut();
        out.setId(account.getId());
        out.setAccountNumber(account.getAccountNumber());
        out.setBalance(account.getBalance());
        out.setIsActive(account.getIsActive());
        return out;
    }

    public void activateAccount(Integer accountId) {
        Account account = customerRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (account.getIsActive()) {
            throw new ApiException("Account is already active");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void blockAccount(Integer accountId) {
        Account account = customerRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (!account.getIsActive()) {
            throw new ApiException("Account is already blocked");
        }

        account.setIsActive(false);
        accountRepository.save(account);
    }

    public List<AccountOut> getCustomerAccounts(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        List<AccountOut> accounts = new ArrayList<>();
        for (Account a : customerRepository.findAccountsByCustomerId(customerId)) {
            AccountOut out = new AccountOut();
            out.setId(a.getId());
            out.setAccountNumber(a.getAccountNumber());
            out.setBalance(a.getBalance());
            out.setIsActive(a.getIsActive());
            accounts.add(out);
        }
        return accounts;
    }

    public void deposit(Integer userId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Account account = customerRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (!account.getCustomer().getId().equals(userId)) {
            throw new ApiException("This account does not belong to you");
        }
        if (!account.getIsActive()) {
            throw new ApiException("Account is not active");
        }
        if (amount <= 0) {
            throw new ApiException("Amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    public void withdraw(Integer userId, Integer accountId, Double amount) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Account account = customerRepository.findAccountById(accountId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (!account.getCustomer().getId().equals(userId)) {
            throw new ApiException("This account does not belong to you");
        }
        if (!account.getIsActive()) {
            throw new ApiException("Account is not active");
        }
        if (amount <= 0) {
            throw new ApiException("Amount must be greater than zero");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    public void transfer(Integer userId, Integer fromAccountId, String toAccountNumber, Double amount) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Account fromAccount = customerRepository.findAccountById(fromAccountId);
        if (fromAccount == null) {
            throw new ApiException("Source account not found");
        }
        if (!fromAccount.getCustomer().getId().equals(userId)) {
            throw new ApiException("Source account does not belong to you");
        }
        if (!fromAccount.getIsActive()) {
            throw new ApiException("Source account is not active");
        }

        Account toAccount = customerRepository.findAccountByAccountNumber(toAccountNumber);
        if (toAccount == null) {
            throw new ApiException("Destination account not found");
        }
        if (!toAccount.getIsActive()) {
            throw new ApiException("Destination account is not active");
        }
        if (amount <= 0) {
            throw new ApiException("Amount must be greater than zero");
        }
        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
