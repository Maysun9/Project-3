package com.example.banksystem.DTO.IN;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountIn {

    @NotBlank(message = "Account number cannot be null")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", message = "Must follow specific format XXXX-XXXX-XXXX-XXXX")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Must be a non-negative decimal number")
    private Double balance;
}