package com.example.banksystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String accountNumber;

    private Double balance;
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}