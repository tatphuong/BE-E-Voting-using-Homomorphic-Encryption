package com.app.repository;

import com.app.models.CandidateJoinElection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateElectionRepo extends CrudRepository<CandidateJoinElection,Long> {
}
