package com.app.repository;

import com.app.models.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateRepo extends CrudRepository<Candidate,Long> {
    Boolean existsByCitizenIdentity(int citizenId);
}
