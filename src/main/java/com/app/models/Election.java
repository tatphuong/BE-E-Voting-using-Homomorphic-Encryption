package com.app.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Entity
@Table
@Data
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @OneToMany(mappedBy = "election")
    private Set<VoterJoinElection> voterElections;
    @OneToMany(mappedBy = "election")
    private Set<CandidateJoinElection> candidateElection;
    @OneToOne
    @JoinColumn(name = "paillier_id", referencedColumnName = "id")
    private Paillier paillier;

    public void addElectionDetail(CandidateJoinElection candidateJoinElection){
        this.candidateElection.add(candidateJoinElection);
    }
}
