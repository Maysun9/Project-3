package com.example.banksystem.API;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
}