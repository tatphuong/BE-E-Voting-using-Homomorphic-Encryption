package com.app.repository;

import com.app.models.ERole;
import com.app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
