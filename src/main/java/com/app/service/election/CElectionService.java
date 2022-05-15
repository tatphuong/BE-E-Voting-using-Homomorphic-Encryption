package com.app.service.election;

import com.app.models.Candidate;
import com.app.models.CandidateJoinElection;
import com.app.models.Election;
import com.app.repository.ICandidateElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Iterable<CandidateJoinElection> findByElection(Election election) {
        return candidateElectionRepo.findByElection(election);
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
    public Optional<CandidateJoinElection> findCandidateJoinElectionByCandidateAndElection(Candidate candidate, Election election) {
        return candidateElectionRepo.findCandidateJoinElectionByCandidateAndElection(candidate,election);
    }

}
