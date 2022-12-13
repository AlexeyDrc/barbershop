/*
package com.ducut.barbershop.services;

import com.ducut.barbershop.models.Role;
import com.ducut.barbershop.models.User;
import com.ducut.barbershop.repos.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public boolean createUser (User user)
    {
        if (userRepository.findByPhoneNumber(user.getPhoneNumber()) != null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return true;
    }
}
*/
