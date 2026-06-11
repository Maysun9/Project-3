package com.example.banksystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/customer/register").permitAll()
                        .requestMatchers("/api/v1/employee/register").hasAuthority("ADMIN")

                        // CUSTOMER
                        .requestMatchers(
                                "/api/v1/customer/delete",
                                "/api/v1/account/create",
                                "/api/v1/account/my-accounts",
                                "/api/v1/account/details/{accountId}",
                                "/api/v1/account/deposit/{accountId}/{amount}",
                                "/api/v1/account/withdraw/{accountId}/{amount}",
                                "/api/v1/account/transfer/{fromAccountId}/{toAccountNumber}/{amount}"
                        ).hasAuthority("CUSTOMER")

                        // EMPLOYEE
                        .requestMatchers("/api/v1/employee/delete").hasAuthority("EMPLOYEE")

                        // EMPLOYEE , ADMIN
                        .requestMatchers(
                                "/api/v1/account/activate/{accountId}",
                                "/api/v1/account/block/{accountId}",
                                "/api/v1/account/customer-accounts/{customerId}"
                        ).hasAnyAuthority("EMPLOYEE", "ADMIN")

                        // ADMIN
                        .requestMatchers(
                                "/api/v1/customer/get",
                                "/api/v1/employee/get"
                        ).hasAuthority("ADMIN")

                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .httpBasic(httpBasic -> {});
        return http.build();
    }
}
