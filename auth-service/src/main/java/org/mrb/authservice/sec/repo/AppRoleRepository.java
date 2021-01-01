package org.mrb.authservice.sec.repo;

import org.mrb.authservice.sec.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String rolName);
}
