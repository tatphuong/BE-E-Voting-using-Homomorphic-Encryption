package com.app.controller;

import com.app.models.ERole;
import com.app.models.Role;
import com.app.models.User;
import com.app.models.UserDTO;
import com.app.payload.request.ChangePasswordRequest;
import com.app.payload.request.CreateUserRequest;
import com.app.payload.request.UpdateUserRequest;
import com.app.payload.response.MessageResponse;
import com.app.service.IUserService;
import com.app.service.role.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    PasswordEncoder encoder;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<UserDTO>> findAllUser() {
        List<User> users=(List<User>) userService.findAll();
        List<UserDTO> userDTOs=users.stream().map(u -> modelMapper.map(u,UserDTO.class)).collect(Collectors.toList());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        UserDTO userDTO=modelMapper.map(userOptional.get(),UserDTO.class);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PostMapping
/*    @PreAuthorize("hasRole('ADMIN')")*/
    public ResponseEntity<?> saveUser(@Valid @RequestBody CreateUserRequest userRequest) {
        if (userService.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        userRequest.setPassword(encoder.encode(userRequest.getPassword()));
        Set<String> strRoles = userRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleService.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        User user=modelMapper.map(userRequest,User.class);
        user.setRoles(roles);
        String email=user.getEmail();
        String username= email.substring(0,email.indexOf("@"));
        user.setUsername(username);
        userService.save(user);
        return new ResponseEntity<>(userRequest, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping ("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest userRequest) {
        Optional<User> optionalUser = userService.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user=optionalUser.get();
        user.setName(userRequest.getName());
        user.setAvatar(userRequest.getAvatar());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setGender(userRequest.getGender());
        userService.save(user);
       UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.remove(id);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Optional<User> userOptional = userService.findByEmailContaining(changePasswordRequest.getEmail());
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user=userOptional.get();
        String passwordOldEncode=encoder.encode(changePasswordRequest.getPasswordOld());
        if(!user.getPassword().equals(passwordOldEncode)){
            return new ResponseEntity<>("password old wrong!!!",HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(changePasswordRequest.getPassword()));
        userService.save(user);
        return new ResponseEntity<>("change password successful ", HttpStatus.OK);
    }
}
