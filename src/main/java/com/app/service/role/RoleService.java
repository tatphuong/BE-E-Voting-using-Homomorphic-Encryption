package com.app.service.role;

import com.app.models.ERole;
import com.app.models.Role;
import com.app.repository.IRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepo roleRepo;
    @Override
    public Iterable<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepo.deleteById(id);

    }

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepo.findByName(name);
    }
}
