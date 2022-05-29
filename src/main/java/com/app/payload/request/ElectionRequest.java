package com.app.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ElectionRequest {
    @NotBlank
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;
}
