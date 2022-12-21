package com.ducut.barbershop.security;

import com.ducut.barbershop.models.Role;
import com.ducut.barbershop.models.UserEntity;
import com.ducut.barbershop.repos.RoleRepository;
import com.ducut.barbershop.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegisterDto registerDto) {
        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
