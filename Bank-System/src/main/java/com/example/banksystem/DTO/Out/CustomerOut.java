package com.example.banksystem.DTO.Out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerOut {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
}