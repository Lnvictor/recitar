package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.Roles;
import com.br.ccbrec.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    public Roles findByRoleName(RoleName roleName);
}
