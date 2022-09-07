package com.app.payload.response;

import com.app.models.Candidate;
import lombok.Data;

@Data
public class NoVotesResponse {
    private Long id;
    private Candidate candidate;
}
