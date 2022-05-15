package com.app.repository;

import com.app.models.Candidate;
import com.app.models.CandidateJoinElection;
import com.app.models.Election;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICandidateElectionRepo extends CrudRepository<CandidateJoinElection,Long> {
    Optional<CandidateJoinElection> findCandidateJoinElectionByCandidateAndElection(Candidate candidate, Election election);
    Iterable<CandidateJoinElection> findByElection(Election election);
}
