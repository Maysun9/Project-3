package com.example.banksystem.Service;

import com.example.banksystem.API.ApiException;
import com.example.banksystem.Model.User;
import com.example.banksystem.Repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authUserRepository.findUserByUsername(username);
        if (user == null)
            throw new ApiException("Wrong Username or Password ");
        return user;
    }
}
