package com.app.service.election;

import com.app.models.VoterJoinElection;
import com.app.service.IGeneralService;

import java.math.BigInteger;

public interface IVElectionService extends IGeneralService<VoterJoinElection> {
    Boolean existsByVoting(Long electionId,Long userId);
    BigInteger findBallot(Long electionId, Long userId);
}
