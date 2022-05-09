package com.app.repository;

import com.app.models.Election;
import org.springframework.data.repository.CrudRepository;

public interface IElectionRepo extends CrudRepository<Election,Long> {
}
