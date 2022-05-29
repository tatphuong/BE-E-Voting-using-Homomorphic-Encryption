package com.app.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CandidateRequest {
    private String name;
    private int citizenIdentity;
    private String avatar;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    private String gender;
    private String occupation;
    private String address;
    private String education;
    private String nationality;
}
