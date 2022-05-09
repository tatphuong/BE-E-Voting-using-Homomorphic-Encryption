package com.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfBirth;
    private String gender;
    private String avatar;
    @JsonFormat(pattern="dd-MM-yyy")
    private Date dayIn;
    private String about;
    private String citizenIdentity;
    private int status;
    private Set<Role> roles ;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        this.status = user.getStatus();
        this.roles = user.getRoles();
    }
}
