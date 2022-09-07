package com.app.controller;

import com.app.models.*;
import com.app.payload.request.ElectionRequest;
import com.app.payload.request.VotingRequest;
import com.app.payload.response.MessageResponse;
import com.app.payload.response.NoVotesResponse;
import com.app.payload.response.TotalVotesResponse;
import com.app.service.UserService;
import com.app.service.candidate.CandidateService;
import com.app.service.election.CElectionService;
import com.app.service.election.ElectionService;
import com.app.service.election.VElectionService;
import com.app.service.paillier.PaillierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private VElectionService vElectionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<ElectionDTO>> findAllElection() {
        List<Election> elections = (List<Election>) electionService.findAll();
        List<ElectionDTO> electionDTOs = elections.stream().map(u -> modelMapper.map(u, ElectionDTO.class)).collect(Collectors.toList());
        if (elections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(electionDTOs, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveElection(@RequestBody Election election) throws Exception {
        Paillier paillier = paillierService.save(new Paillier(8));
        election.setPaillier(paillier);
        return new ResponseEntity<>(electionService.save(election), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ElectionDTO> findElectionById(@PathVariable Long id) {
        Optional<Election> electionOptional = electionService.findById(id);
        ElectionDTO userDTO = modelMapper.map(electionOptional.get(), ElectionDTO.class);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * add a candidate to election
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/{idCandidate}")
    public ResponseEntity<?> addCandidate(@PathVariable Long id, @PathVariable Long idCandidate) throws Exception {
        Optional<Election> optionalElection = electionService.findById(id);
        if (!optionalElection.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election = optionalElection.get();
        Optional<Candidate> candidateOptional = candidateService.findById(idCandidate);
        if (!candidateOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Paillier paillier = election.getPaillier();
        Candidate candidate = candidateOptional.get();
        CandidateJoinElection candidateJoinElection = new CandidateJoinElection();
        candidateJoinElection.setCandidate(candidate);
        candidateJoinElection.setElection(election);
        candidateJoinElection.setNumberOfVotes(paillier.encrypt(new BigInteger("0")));
        cElectionService.save(candidateJoinElection);
        return new ResponseEntity<>(new MessageResponse("Add candidate successful"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping ("/{id}")
    public ResponseEntity<ElectionDTO> updateElection(@PathVariable Long id, @RequestBody ElectionRequest electionRequest) {
        Optional<Election> optionalElection = electionService.findById(id);
        if (!optionalElection.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election=optionalElection.get();
        election.setName(electionRequest.getName());
        election.setEndTime(electionRequest.getEndTime());
        election.setStartTime(electionRequest.getStartTime());
        electionService.save(election);
        ElectionDTO electionDTO = modelMapper.map(election,ElectionDTO.class);
        return new ResponseEntity<>(electionDTO, HttpStatus.OK);
    }

    /**
     * ballot is encrypt id candidate
     * save in database and calculator total vote (decrypt)
     *
     * @param id
     * @param votingRequest
     * @return ballot of voter after encrypt
     * @throws Exception
     */
    @PostMapping("/{id}/voting")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> voting(@PathVariable Long id, @RequestBody VotingRequest votingRequest) throws Exception {
        Optional<Election> electionOptional = electionService.findById(id);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election = electionOptional.get();
        Date presentDate=new Date();
        if(election.getEndTime().before(presentDate)){
            System.out.println(election.getEndTime());
            return new ResponseEntity<>(new MessageResponse("Da het han bau cu"),HttpStatus.BAD_REQUEST);
        }
        User user = userService.findById(votingRequest.getUserId()).get();
        Paillier paillier = election.getPaillier();
        BigInteger ballot = paillier.encrypt(new BigInteger(Long.toString(votingRequest.getCandidateId())));
        VoterJoinElection voterJoinElection = new VoterJoinElection();
        voterJoinElection.setElection(election);
        voterJoinElection.setBallot(ballot);
        voterJoinElection.setUser(user);
        vElectionService.save(voterJoinElection);
        Candidate candidate = candidateService.findById(votingRequest.getCandidateId()).get();
        CandidateJoinElection cje = cElectionService.findCandidateJoinElectionByCandidateAndElection(candidate, election).get();
        BigInteger totalVoteCand = cje.getNumberOfVotes().multiply(ballot).mod(paillier.getNsquare());
        cje.setNumberOfVotes(totalVoteCand);
        cElectionService.save(cje);
        return new ResponseEntity<>(ballot, HttpStatus.OK);
    }

    /**
     *
     * @param id of Election
     * @return total vote of candidates in election
     * @throws Exception
     */
    @GetMapping("/{id}/totalVotes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> totalVote(@PathVariable Long id) throws Exception {
        Optional<Election> electionOptional = electionService.findById(id);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election = electionOptional.get();
        List<CandidateJoinElection> cjeList = (List<CandidateJoinElection>) cElectionService.findByElection(election);
        Paillier paillier = election.getPaillier();
        //mapper to totalVote response
        List<TotalVotesResponse> totalVotesResponseList = cjeList.stream().map(u -> modelMapper.map(u, TotalVotesResponse.class)).collect(Collectors.toList());
        Date presentDate=new Date();
        if(election.getEndTime().after(presentDate)){
            System.out.println(election.getEndTime());
            List<NoVotesResponse> noVotesResponses= totalVotesResponseList.stream().map(t -> modelMapper.map(t,NoVotesResponse.class)).collect(Collectors.toList());
            return new ResponseEntity<>(noVotesResponses, HttpStatus.OK);
        }
        for (TotalVotesResponse votesResponse : totalVotesResponseList) {
            BigInteger candidateId = BigInteger.valueOf(votesResponse.getCandidate().getId());
            //decrypt totalVote of candidate then divide id candidate
            votesResponse.setNumberOfVotes(paillier.decrypt(votesResponse.getNumberOfVotes()).divide(candidateId));
        }
        return new ResponseEntity<>(totalVotesResponseList, HttpStatus.OK);
    }
    @GetMapping("/{id}/totalVotesAD")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> totalVoteForAD(@PathVariable Long id) throws Exception {
        Optional<Election> electionOptional = electionService.findById(id);
        if (!electionOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Election election = electionOptional.get();
        List<CandidateJoinElection> cjeList = (List<CandidateJoinElection>) cElectionService.findByElection(election);
        Paillier paillier = election.getPaillier();
        //mapper to totalVote response
        List<TotalVotesResponse> totalVotesResponseList = cjeList.stream().map(u -> modelMapper.map(u, TotalVotesResponse.class)).collect(Collectors.toList());
        for (TotalVotesResponse votesResponse : totalVotesResponseList) {
            BigInteger candidateId = BigInteger.valueOf(votesResponse.getCandidate().getId());
            //decrypt totalVote of candidate then divide id candidate
            votesResponse.setNumberOfVotes(paillier.decrypt(votesResponse.getNumberOfVotes()).divide(candidateId));
        }
        return new ResponseEntity<>(totalVotesResponseList, HttpStatus.OK);
    }
    @GetMapping("/{id}/checkVoted/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Boolean checkVoted(@PathVariable(name = "id") Long id,@PathVariable Long userId) throws Exception {

        Boolean isVoted=vElectionService.existsByVoting(id, userId);
        return isVoted;
    }
    @GetMapping("/{id}/getBallot/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public BigInteger getBallot(@PathVariable(name = "id") Long id,@PathVariable Long userId) throws Exception {

        BigInteger ballot=vElectionService.findBallot(id,userId);
        return ballot;
    }
}
