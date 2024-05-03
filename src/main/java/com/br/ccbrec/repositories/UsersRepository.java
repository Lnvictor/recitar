package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
