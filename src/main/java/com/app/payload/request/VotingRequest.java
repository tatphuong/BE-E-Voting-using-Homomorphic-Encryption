package com.app.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class VotingRequest {
    @NotBlank
    private Long candidateId;
    @NotBlank
    private Long userId;
}
