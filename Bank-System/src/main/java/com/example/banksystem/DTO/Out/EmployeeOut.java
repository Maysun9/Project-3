package com.example.banksystem.DTO.Out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeOut {
    private Integer id;
    private String username;
    private String name;
    private String position;
    private Double salary;
}