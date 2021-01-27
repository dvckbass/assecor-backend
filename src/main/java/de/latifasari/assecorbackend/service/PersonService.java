package de.latifasari.assecorbackend.service;

import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private static long id = 1;

    private Map<Integer, String> colorMap = new HashMap<>(){{
        put(1, "blau");
        put(2, "grün");
        put(3, "violett");
        put(4, "rot");
        put(5, "gelb");
        put(6, "türkis");
        put(7, "weiß");
    }};

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
        initData();
    }

    public void initData() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/sample-input.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> dataList = Arrays.asList(line.split("\\s*,\\s*"));
                if(dataList.size() == 4) {
                    insertDataToPerson(dataList);
                } else {
                    String nextLine = scanner.nextLine();
                    List<String> nextDataList = Arrays.asList(nextLine.split("\\s*,\\s*"));
                    List<String> newDataList = Stream.concat(dataList.stream(), nextDataList.stream()).collect(Collectors.toList());
                    insertDataToPerson(newDataList);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertDataToPerson(List<String> list) {
        Person person = new Person();
        String lastName = list.get(0);
        String name = list.get(1);
        String address = list.get(2);
        String zipCode = address.substring(0, address.indexOf(' '));
        String city = address.substring(address.indexOf(' ') + 1);
        int colorId = Integer.parseInt(list.get(3));
        person.setId(id++);
        person.setLastName(lastName);
        person.setName(name);
        person.setZipCode(zipCode);
        person.setCity(city);
        colorMap.keySet().forEach(color -> {
            if(color == colorId) {
                person.setColor(colorMap.get(color));
            }
        });
        this.personRepository.save(person);
    }

    public Iterable<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Person findPersonById(Long id) {
        return this.personRepository.findById(id).get();
    }

    public Iterable<Person> findPersonByColor(String color) {
        return this.personRepository.getPersonByColor(color);
    }
}
