package com.app.service.election;

import com.app.models.Election;
import com.app.repository.IElectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElectionService implements IElectionService{
    @Autowired
    private IElectionRepo electionRepo;

    @Override
    public Iterable<Election> findAll() {
        return electionRepo.findAll();
    }

    @Override
    public Optional<Election> findById(Long id) {
        return electionRepo.findById(id);
    }

    @Override
    public Election save(Election election) {
        return electionRepo.save(election);
    }

    @Override
    public void remove(Long id) {
        electionRepo.deleteById(id);

    }
}
