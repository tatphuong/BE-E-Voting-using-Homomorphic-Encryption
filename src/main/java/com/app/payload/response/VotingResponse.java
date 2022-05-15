package com.app.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class VotingResponse {
    private BigInteger ballot;

    public VotingResponse(BigInteger ballot) {
        this.ballot = ballot;
    }
}
