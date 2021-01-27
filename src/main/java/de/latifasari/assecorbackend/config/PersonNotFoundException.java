package de.latifasari.assecorbackend.config;

public class PersonNotFoundException extends RuntimeException{

    PersonNotFoundException(Long id) {
        super("Person " + id + "kann nicht gefunden werden");
    }
}
