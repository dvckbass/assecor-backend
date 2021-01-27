package de.latifasari.assecorbackend.controller;

import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public Iterable<Person> getPersons() {
        return this.personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return this.personRepository.findById(id).get();
    }

    @GetMapping("/color/{color}")
    public Iterable<Person> getPersonByColor(@PathVariable String color) {
        return this.personRepository.findByColor(color);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return this.personRepository.save(person);
    }
}
