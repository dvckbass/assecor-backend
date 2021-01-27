package de.latifasari.assecorbackend.controller;

import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Iterable<Person> getPersons() {
        return this.personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return this.personService.findPersonById(id);
    }

    @GetMapping("/color/{color}")
    public Iterable<Person> getPersonByColor(@PathVariable String color) {
        return this.personService.findPersonByColor(color);
    }
}
