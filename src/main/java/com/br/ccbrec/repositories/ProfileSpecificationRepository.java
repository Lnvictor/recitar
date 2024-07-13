package com.br.ccbrec.repositories;

import com.br.ccbrec.entities.ProfileSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileSpecificationRepository extends JpaRepository<ProfileSpecification, Long> {

    @Query("select ps FROM ProfileSpecification ps " +
            "INNER JOIN User u ON  u.id = ps.user.id WHERE u.username = :username")
    ProfileSpecification getByUsername(String username);
}
