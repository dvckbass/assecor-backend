package de.latifasari.assecorbackend.config;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(Long id) {
        super("Person " + id + " kann nicht gefunden werden");
    }
}
