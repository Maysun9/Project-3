package com.example.banksystem.Controller;

import com.example.banksystem.API.ApiResponse;
import com.example.banksystem.DTO.IN.CustomerRegisterIn;
import com.example.banksystem.Service.AuthCustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class AuthCustomerController {

    private final AuthCustomerService authCustomerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerRegisterIn dto) {
        authCustomerService.registerCustomer(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCustomers() {
        return ResponseEntity.status(200).body(authCustomerService.getAllCustomers());
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer customerId) {
        authCustomerService.deleteCustomer(customerId);
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
}