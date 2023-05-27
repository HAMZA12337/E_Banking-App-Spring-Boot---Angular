package com.lafryhi.yassine.digital.banking.security.repositories;

import com.lafryhi.yassine.digital.banking.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findAppRoleByRoleName(String username);
}
