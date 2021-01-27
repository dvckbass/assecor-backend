package de.latifasari.assecorbackend.repository;

import de.latifasari.assecorbackend.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
    Iterable<Person> findByColor(String color);
    Person findFirstByOrderByIdAsc();

}
