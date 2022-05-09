package com.app.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.*;

@Getter
@Setter
public class CreateUserRequest {
  @NotBlank
  private String name;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  private Set<String> roles;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateOfBirth;
  @ColumnDefault("Male")
  private String gender;
  private String avatar;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dayIn;
  private int citizenIdentity;
  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  public String getEmail() {
    return email;
  }


  public String getPassword() {
    return password;
  }

  public Set<String> getRoles() {
    return this.roles;
  }

}
