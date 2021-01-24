package de.latifasari.assecorbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    private long id;
    private String name;
    private String lastName;
    private int zipCode;
    private String city;
    private String color;

    public Person() {

    }

    public Person(long id, String name, String lastName, int zipCode, String city, String color) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.zipCode = zipCode;
        this.city = city;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
