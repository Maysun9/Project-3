package com.example.banksystem.Controller;

import com.example.banksystem.API.ApiResponse;
import com.example.banksystem.DTO.IN.EmployeeRegisterIn;
import com.example.banksystem.Model.User;
import com.example.banksystem.Service.AuthEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class AuthEmployeeController {

    private final AuthEmployeeService authEmployeeService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody @Valid EmployeeRegisterIn dto) {
        authEmployeeService.registerEmployee(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee registered successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.status(200).body(authEmployeeService.getAllEmployees());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@AuthenticationPrincipal User user) {
        authEmployeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }
}