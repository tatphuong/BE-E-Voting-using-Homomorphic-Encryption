package com.app.controller;

import com.app.models.Candidate;
import com.app.payload.request.CandidateRequest;
import com.app.payload.response.MessageResponse;
import com.app.service.candidate.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CandidateService candidateService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Candidate>> findAllCandidate() {
        List<Candidate> candidates= (List<Candidate>) candidateService.findAll();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCandidate(@RequestBody CandidateRequest candidateRequest) {
        if (candidateService.existsByCitizenIdentity(candidateRequest.getCitizenIdentity())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Candidate already exists with same citizen ID!"));
        }
        Candidate candidate= modelMapper.map(candidateRequest,Candidate.class);

        return new ResponseEntity<>(candidateService.save(candidate), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping ("/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody CandidateRequest candidateReq) {
        Optional<Candidate> optionalCandidate = candidateService.findById(id);
        if (optionalCandidate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Candidate candidate=modelMapper.map(candidateReq,Candidate.class);
        candidate.setId(id);
        return new ResponseEntity<>(candidateService.save(candidate), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> findCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidateOptional = candidateService.findById(id);
        Candidate candidate=candidateOptional.get();
        if (!candidateOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

}
