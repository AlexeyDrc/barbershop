package com.ducut.barbershop.security;

import com.ducut.barbershop.models.UserEntity;
import com.ducut.barbershop.repos.UserRepository;

public interface UserService {

    void saveUser(RegisterDto registerDto);
    void saveMaster(RegisterDto registerDto);
    UserEntity findUserByUsername(String username);
}
