package com.app.service.election;

import com.app.models.VoterJoinElection;
import com.app.repository.IUserElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VElectionService implements IVElectionService{
    @Autowired
    private IUserElectionRepo iUserElectionRepo;
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
        return iUserElectionRepo.save(voterJoinElection);
    }

    @Override
    public void remove(Long id) {

    }
}
