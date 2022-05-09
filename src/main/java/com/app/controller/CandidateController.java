package com.app.controller;

import com.app.models.Candidate;
import com.app.models.User;
import com.app.models.UserDTO;
import com.app.payload.response.MessageResponse;
import com.app.service.candidate.CandidateService;
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
    private CandidateService candidateService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<Candidate>> findAllCandidate() {
        List<Candidate> candidates= (List<Candidate>) candidateService.findAll();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCandidate(@RequestBody Candidate candidateRequest) {
        if (candidateService.existsByCitizenIdentity(candidateRequest.getCitizenIdentity())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Candidate already exists!"));
        }

        return new ResponseEntity<>(candidateService.save(candidateRequest), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping ("/{id}")
    public ResponseEntity<Candidate> updateUser(@PathVariable Long id, @RequestBody Candidate candidate) {
        Optional<Candidate> optionalCandidate = candidateService.findById(id);
        if (optionalCandidate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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
