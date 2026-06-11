package com.example.banksystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts;
}
