package com.app.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ChangePasswordRequest {
    @Email
    private String email;
    private String passwordOld;
    private String password;
}
