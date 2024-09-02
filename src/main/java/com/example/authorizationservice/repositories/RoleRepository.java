package com.example.authorizationservice.repositories;

import com.example.authorizationservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
