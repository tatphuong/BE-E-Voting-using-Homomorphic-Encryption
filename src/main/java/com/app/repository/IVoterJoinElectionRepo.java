package com.app.repository;

import com.app.models.VoterJoinElection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IVoterJoinElectionRepo extends CrudRepository<VoterJoinElection,Long> {
    @Query("select case when count(v)>0 then true else false end FROM VoterJoinElection v where v.election.id=?1 and v.user.id=?2")
    Boolean existsByVoting(Long electionId,Long userId);
    @Query("select v.ballot from VoterJoinElection v where v.election.id=?1 and v.user.id=?2")
    BigInteger findBallot(Long electionId,Long userId);
}
