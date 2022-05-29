package com.app.service.election;

import com.app.models.VoterJoinElection;
import com.app.repository.IUserElectionRepo;
import com.app.repository.IVoterJoinElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

@Service
public class VElectionService implements IVElectionService{
    @Autowired
    private IVoterJoinElectionRepo iVoterJoinElectionRepo;
    @Override
    public Iterable<VoterJoinElection> findAll() {
        return null;
    }

    @Override
    public Optional<VoterJoinElection> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public VoterJoinElection save(VoterJoinElection voterJoinElection) {
        return iVoterJoinElectionRepo.save(voterJoinElection);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Boolean existsByVoting(Long electionId, Long userId) {
        return iVoterJoinElectionRepo.existsByVoting(electionId,userId);
    }

    @Override
    public BigInteger findBallot(Long electionId, Long userId) {
        return iVoterJoinElectionRepo.findBallot(electionId,userId);
    }
}
