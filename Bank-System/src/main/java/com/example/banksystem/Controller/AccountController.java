package com.example.banksystem.Controller;

import com.example.banksystem.API.ApiResponse;
import com.example.banksystem.DTO.IN.AccountIn;
import com.example.banksystem.DTO.Out.AccountOut;
import com.example.banksystem.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // CUSTOMER
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody @Valid AccountIn dto) {
        accountService.createAccount(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Account created successfully"));
    }

    // CUSTOMER
    @GetMapping("/my-accounts")
    public ResponseEntity<?> getMyAccounts() {
        return ResponseEntity.status(200).body(accountService.getMyAccounts());
    }

    // CUSTOMER
    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountOut> getAccountDetails(@PathVariable Integer accountId) {
        return ResponseEntity.status(200).body(accountService.getAccountDetails(accountId));
    }

    // CUSTOMER
    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(@PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit successful"));
    }

    // CUSTOMER
    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<ApiResponse> withdraw(@PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.withdraw(accountId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Withdrawal successful"));
    }

    // CUSTOMER
    @PutMapping("/transfer/{fromAccountId}/{toAccountNumber}/{amount}")
    public ResponseEntity<?> transfer(@PathVariable Integer fromAccountId, @PathVariable String toAccountNumber, @PathVariable Double amount) {
        accountService.transfer(fromAccountId, toAccountNumber, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer successful"));
    }

    // EMPLOYEE
    @PutMapping("/activate/{accountId}")
    public ResponseEntity<?> activateAccount(@PathVariable Integer accountId) {
        accountService.activateAccount(accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated successfully"));
    }

    // EMPLOYEE
    @PutMapping("/block/{accountId}")
    public ResponseEntity<?> blockAccount(@PathVariable Integer accountId) {
        accountService.blockAccount(accountId);
        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }

    // EMPLOYEE
    @GetMapping("/customer-accounts/{customerId}")
    public ResponseEntity<List<AccountOut>> getCustomerAccounts(@PathVariable Integer customerId) {
        return ResponseEntity.status(200).body(accountService.getCustomerAccounts(customerId));
    }
}