package de.latifasari.assecorbackend.repository;

import de.latifasari.assecorbackend.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
