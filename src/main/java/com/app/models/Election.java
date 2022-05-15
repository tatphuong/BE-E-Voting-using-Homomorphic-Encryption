package com.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @OneToMany(mappedBy = "election", fetch = FetchType.LAZY)
    private Set<VoterJoinElection> voterElections;
    @JsonIgnore
    @OneToMany(mappedBy = "election",fetch = FetchType.LAZY)
    private Set<CandidateJoinElection> candidateElection;
    @OneToOne
    @JoinColumn(name = "paillier_id", referencedColumnName = "id")
    private Paillier paillier;

    public void addElectionDetail(CandidateJoinElection candidateJoinElection){
        this.candidateElection.add(candidateJoinElection);
    }

    @Override
    public String toString() {
        return "Election{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", paillier=" + paillier +
                '}';
    }
}
