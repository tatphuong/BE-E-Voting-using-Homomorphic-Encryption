package com.app.service.paillier;

import com.app.models.Paillier;
import com.app.repository.IPaillierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaillierService implements IPaillierService {
    @Autowired
    private IPaillierRepo paillierRepo;

    @Override
    public Iterable<Paillier> findAll() {
        return paillierRepo.findAll();
    }

    @Override
    public Optional<Paillier> findById(Long id) {
        return paillierRepo.findById(id);
    }

    @Override
    public Paillier save(Paillier paillier) {
        return paillierRepo.save(paillier);
    }

    @Override
    public void remove(Long id) {
        paillierRepo.deleteById(id);
    }
}
