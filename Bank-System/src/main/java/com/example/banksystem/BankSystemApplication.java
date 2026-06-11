package com.example.banksystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

    @SpringBootApplication
    public class BankSystemApplication {
        public static void main(String[] args) {
            //عشان اطلع الهاش باسورد واقارن لان كان يطلع لي ان اوثرايز
           // System.out.println(new BCryptPasswordEncoder().encode("admin123"));
            SpringApplication.run(BankSystemApplication.class, args);
        }
    }

