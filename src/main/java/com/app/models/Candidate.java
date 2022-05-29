package com.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateOfBirth;
    private String gender;
    private String occupation;
    private String address;
    private String education;
    private String nationality;
    @JsonIgnore
    @OneToMany(mappedBy = "candidate")
    Set<CandidateJoinElection> candidateElection;
    public void addElectionDetail(CandidateJoinElection candidateJoinElection){
        this.candidateElection.add(candidateJoinElection);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", citizenIdentity=" + citizenIdentity +
                ", avatar='" + avatar + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", address='" + address + '\'' +
                ", education='" + education + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
