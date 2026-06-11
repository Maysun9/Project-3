package com.example.banksystem.Service;

import com.example.banksystem.API.ApiException;
import com.example.banksystem.DTO.IN.CustomerRegisterIn;
import com.example.banksystem.DTO.Out.CustomerOut;
import com.example.banksystem.Model.Customer;
import com.example.banksystem.Model.User;
import com.example.banksystem.Repository.AuthUserRepository;
import com.example.banksystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthCustomerService {

    private final AuthUserRepository authUserRepository;
    private final CustomerRepository customerRepository;

    public void registerCustomer(CustomerRegisterIn dto) {
        if (authUserRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        if (authUserRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }
        if (customerRepository.findCustomerByPhoneNumber(dto.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("CUSTOMER");

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);
        user.setCustomer(customer);

        authUserRepository.save(user);
    }

    public List<CustomerOut> getAllCustomers() {
        List<CustomerOut> customers = new ArrayList<>();
        for (Customer c : customerRepository.findAll()) {
            CustomerOut out = new CustomerOut();
            out.setId(c.getId());
            out.setUsername(c.getUser().getUsername());
            out.setName(c.getUser().getName());
            out.setEmail(c.getUser().getEmail());
            out.setPhoneNumber(c.getPhoneNumber());
            customers.add(out);
        }
        return customers;
    }

    public void deleteCustomer(Integer userId) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        customerRepository.delete(customer);
    }
}