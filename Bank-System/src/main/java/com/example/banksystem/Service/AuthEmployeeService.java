package com.example.banksystem.Service;

import com.example.banksystem.API.ApiException;
import com.example.banksystem.DTO.IN.EmployeeRegisterIn;
import com.example.banksystem.DTO.Out.EmployeeOut;
import com.example.banksystem.Model.Employee;
import com.example.banksystem.Model.User;
import com.example.banksystem.Repository.AuthUserRepository;
import com.example.banksystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthEmployeeService {

    private final AuthUserRepository authUserRepository;
    private final EmployeeRepository employeeRepository;


    public void registerEmployee(EmployeeRegisterIn dto) {
        if (authUserRepository.findUserByUsername(dto.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }
        if (authUserRepository.findUserByEmail(dto.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("EMPLOYEE");

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);
        user.setEmployee(employee);

        authUserRepository.save(user);
    }

    public List<EmployeeOut> getAllEmployees() {
        List<EmployeeOut> employees = new ArrayList<>();
        for (Employee e : employeeRepository.findAll()) {
            EmployeeOut out = new EmployeeOut();
            out.setId(e.getId());
            out.setUsername(e.getUser().getUsername());
            out.setName(e.getUser().getName());
            out.setPosition(e.getPosition());
            out.setSalary(e.getSalary());
            employees.add(out);
        }
        return employees;
    }

    public void deleteEmployee(Integer employeeId) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if (employee == null){
            throw new ApiException("Employee not found");
        }
        employeeRepository.delete(employee);
    }
}