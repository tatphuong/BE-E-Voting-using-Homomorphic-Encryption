package com.app.service;

import com.app.models.User;
import com.app.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepo userRepo;
    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);

    }

    @Override
    public void remove(Long id) {
        userRepo.deleteById(id);

    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmailContaining(String email) {
        return userRepo.findByEmailContaining(email);
    }

    @Override
    public Optional<User> findUserByCitizenIdentity(String citizenId) {
        return userRepo.findUserByCitizenIdentity(citizenId);
    }
}
