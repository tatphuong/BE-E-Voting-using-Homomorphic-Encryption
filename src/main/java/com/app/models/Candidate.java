package com.app.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int citizenIdentity;
    private String avatar;
    private Date dateOfBirth;
    private String gender;
    private String occupation;
    private String address;
    private String education;
    private String nationality;
    @OneToMany(mappedBy = "candidate")
    Set<CandidateJoinElection> candidateElection;
    public void addElectionDetail(CandidateJoinElection candidateJoinElection){
        this.candidateElection.add(candidateJoinElection);
    }

}
