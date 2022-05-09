package com.app.service;

import com.app.models.User;

import java.util.Optional;

public interface IUserService extends IGeneralService<User>{
    Boolean existsByEmail(String email);
    Optional<User> findByEmailContaining(String email);
    Optional<User> findUserByCitizenIdentity(String citizenId);
}
