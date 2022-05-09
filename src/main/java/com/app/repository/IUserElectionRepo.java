package com.app.repository;

import com.app.models.VoterJoinElection;
import org.springframework.data.repository.CrudRepository;

public interface IUserElectionRepo extends CrudRepository<VoterJoinElection,Long> {
}
