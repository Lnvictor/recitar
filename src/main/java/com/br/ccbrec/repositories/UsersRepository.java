package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.Roles;
import com.br.ccbrec.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public List<User> findByRoles(Roles roles);
}
