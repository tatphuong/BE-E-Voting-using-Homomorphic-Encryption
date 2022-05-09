package com.app.service.election;

import com.app.models.CandidateJoinElection;
import com.app.repository.ICandidateElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CElectionService implements ICElectionService{
    @Autowired
    private ICandidateElectionRepo candidateElectionRepo;
    @Override
    public Iterable<CandidateJoinElection> findAll() {
        return null;
    }

    @Override
    public Optional<CandidateJoinElection> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CandidateJoinElection save(CandidateJoinElection candidateJoinElection) {
        return candidateElectionRepo.save(candidateJoinElection);
    }

    @Override
    public void remove(Long id) {

    }
}
