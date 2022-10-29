package com.ducut.barbershop.repos;

import com.ducut.barbershop.models.Auth.ERole;
import com.ducut.barbershop.models.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
