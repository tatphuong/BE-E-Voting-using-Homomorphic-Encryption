package com.app.repository;

import com.app.models.Paillier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaillierRepo extends CrudRepository<Paillier,Long> {
}
