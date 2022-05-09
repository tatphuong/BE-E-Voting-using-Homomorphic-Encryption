package com.app.repository;

import com.app.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepo extends CrudRepository<User,Long> {
    Optional<User> findByEmailContaining(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByCitizenIdentity(String citizenId);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
