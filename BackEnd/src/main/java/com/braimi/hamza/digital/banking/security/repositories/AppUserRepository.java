package com.braimi.hamza.digital.banking.security.repositories;

import com.braimi.hamza.digital.banking.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findAppUserByUsername(String username);
}
