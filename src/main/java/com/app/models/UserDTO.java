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
    private String address;
    private String citizenIdentity;
    private int status;
    private Set<Role> roles ;

}
