package com.app.service.role;

import com.app.models.ERole;
import com.app.models.Role;
import com.app.service.IGeneralService;

import java.util.Optional;

public interface IRoleService extends IGeneralService<Role> {
    Optional<Role> findByName(ERole name);
}
