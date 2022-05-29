package com.app.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    @ColumnDefault("Male")
    private String gender;
    private String avatar;
    private String address;
    private String citizenIdentity;
}
