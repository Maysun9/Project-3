package com.example.banksystem.Repository;

import com.example.banksystem.Model.Account;
import com.example.banksystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.id = ?1")
    Customer findCustomerById(Integer id);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = ?1")
    Customer findCustomerByPhoneNumber(String phoneNumber);

    // الاكاونت مرتبط بالكستمر
    @Query("SELECT a FROM Account a WHERE a.id = ?1")
    Account findAccountById(Integer id);

    @Query("SELECT a FROM Account a WHERE a.customer.id = ?1")
    List<Account> findAccountsByCustomerId(Integer customerId);

    @Query("SELECT a FROM Account a WHERE a.accountNumber = ?1")
    Account findAccountByAccountNumber(String accountNumber);
}
