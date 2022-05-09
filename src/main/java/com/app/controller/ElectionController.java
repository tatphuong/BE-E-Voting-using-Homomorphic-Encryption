package com.app.controller;

import com.app.models.*;
import com.app.service.UserService;
import com.app.service.candidate.CandidateService;
import com.app.service.election.CElectionService;
import com.app.service.election.ElectionService;
import com.app.service.paillier.PaillierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/elections")
public class ElectionController {
    @Autowired
    private ElectionService electionService;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PaillierService paillierService;
    @Autowired
    private CElectionService cElectionService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ElectionDTO>> findAllElection() {
        List<Election> elections= (List<Election>) electionService.findAll();
        List<ElectionDTO> electionDTOs=elections.stream().map(u -> modelMapper.map(u,ElectionDTO.class)).collect(Collectors.toList());
        if (elections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(electionDTOs, HttpStatus.OK);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveElection(@RequestBody Election election) throws Exception {
        Paillier paillier=paillierService.save(new Paillier(8));
        election.setPaillier(paillier);
        return new ResponseEntity<>(electionService.save(election), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ElectionDTO> findElectionById(@PathVariable Long id) {
        Optional<Election> electionOptional = electionService.findById(id);
        ElectionDTO userDTO=modelMapper.map(electionOptional.get(),ElectionDTO.class);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * add a candidate to election
     * @param id
     * @param idCandidate
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/{id}/{idCandidate}")
    public ResponseEntity<?> addCandidate(@PathVariable Long id, @PathVariable Long idCandidate) {
        Optional<Election> optionalElection = electionService.findById(id);
        if (!optionalElection.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election=optionalElection.get();
        Optional<Candidate> candidateOptional = candidateService.findById(idCandidate);
        if (!candidateOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Candidate candidate=candidateOptional.get();
        CandidateJoinElection candidateJoinElection=new CandidateJoinElection();
        candidateJoinElection.setCandidate(candidate);
        cElectionService.save(candidateJoinElection);
/*        candidateJoinElection.setElection(election);
        candidate.addElectionDetail(candidateJoinElection);*/
        election.addElectionDetail(candidateJoinElection);
        return new ResponseEntity<>(electionService.save(election), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping ("/{id}/addAll")
    public ResponseEntity<?> addAllUser(@PathVariable Long id) {
        Optional<Election> electionOptional=electionService.findById(id);
        Election election=modelMapper.map(electionOptional.get(),Election.class);
        Iterable<User> users=userService.findAll();
        return new ResponseEntity<>(electionService.save(election), HttpStatus.OK);
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ElectionDTO> voting(@PathVariable Long id) {
        Optional<Election> electionOptional = electionService.findById(id);
        ElectionDTO userDTO=modelMapper.map(electionOptional.get(),ElectionDTO.class);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
