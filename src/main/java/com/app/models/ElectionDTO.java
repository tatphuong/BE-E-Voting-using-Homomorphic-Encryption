package com.app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class ElectionDTO {
    private Long id;
    private String name;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date startTime;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date endTime;
    private Set<CandidateJoinElection> candidateElection;
}
