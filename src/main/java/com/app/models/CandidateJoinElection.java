package com.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@Table
public class CandidateJoinElection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "election_id")
    private Election election;
    private BigInteger numberOfVotes;
}
