package com.app.payload.response;

import com.app.models.Candidate;
import lombok.Data;

import java.math.BigInteger;

@Data
public class TotalVotesResponse {
    private Long id;
    private BigInteger numberOfVotes;
    private Candidate candidate;
}
