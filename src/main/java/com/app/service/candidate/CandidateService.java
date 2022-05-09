package com.app.service.candidate;

import com.app.models.Candidate;
import com.app.repository.ICandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CandidateService implements ICandidateService{
    @Autowired
    private ICandidateRepo candidateRepo;
    @Override
    public Iterable<Candidate> findAll() {
        return candidateRepo.findAll();
    }

    @Override
    public Optional<Candidate> findById(Long id) {
        return candidateRepo.findById(id);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepo.save(candidate);
    }

    @Override
    public void remove(Long id) {
        candidateRepo.deleteById(id);

    }

    @Override
    public Boolean existsByCitizenIdentity(int citizenId) {
        return candidateRepo.existsByCitizenIdentity(citizenId);
    }

}
