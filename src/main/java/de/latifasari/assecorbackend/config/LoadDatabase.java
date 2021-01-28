package de.latifasari.assecorbackend.config;

import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    private Map<Integer, String> colorMap = new HashMap<>(){{
        put(1, "blau");
        put(2, "grün");
        put(3, "violett");
        put(4, "rot");
        put(5, "gelb");
        put(6, "türkis");
        put(7, "weiß");
    }};

    @Bean
    CommandLineRunner initDatabase(PersonRepository repository) {
        List<Person> persons = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/sample-input.csv"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> dataList = Arrays.asList(line.split("\\s*,\\s*"));
                if(dataList.size() == 4) {
                    persons.add( getPersonFromData(dataList));
                } else {
                    String nextLine = scanner.nextLine();
                    List<String> nextDataList = Arrays.asList(nextLine.split("\\s*,\\s*"));
                    List<String> newDataList = Stream.concat(dataList.stream(), nextDataList.stream()).collect(Collectors.toList());
                    persons.add(getPersonFromData(newDataList));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return args -> {
          persons.forEach(person -> {
              log.info("Erstellung einer Person: " + repository.save(person));
          });
        };


    }

    public Person getPersonFromData(List<String> list) {
        Person person = new Person();
        String lastName = list.get(0);
        String name = list.get(1);
        String address = list.get(2);
        String zipCode = address.substring(0, address.indexOf(' '));
        String city = address.substring(address.indexOf(' ') + 1);
        int colorId = Integer.parseInt(list.get(3));
        person.setLastname(lastName);
        person.setName(name);
        person.setZipcode(zipCode);
        person.setCity(city);
        colorMap.keySet().forEach(color -> {
            if(color == colorId) {
                person.setColor(colorMap.get(color));
            }
        });
        return person;
    }
}
