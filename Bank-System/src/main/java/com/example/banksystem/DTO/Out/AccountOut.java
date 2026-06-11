package com.example.banksystem.DTO.Out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountOut {
    private Integer id;
    private String accountNumber;
    private Double balance;
    private Boolean isActive;
}
