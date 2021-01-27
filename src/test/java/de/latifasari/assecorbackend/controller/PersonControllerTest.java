package de.latifasari.assecorbackend.controller;

import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    // Test für /persons
    @Test
    public void getPersons() throws Exception {
        when(personService.findAll())
                .thenReturn(List.of(new Person(1, "Hans", "Müller", "67742", "Lauterecken", "blau" )));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Hans"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Müller"));
    }

    // Test für /persons/{id}
    @Test
    public void getPersonById() throws Exception {
        Person person = new Person(50L, "Ardilla", "Latifasari", "55555", "Berlin", "blau");
        when(personService.findPersonById(50L)).thenReturn(person);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/50"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Ardilla"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Latifasari"));
    }

    // Test für /persons/color/{color}
    @Test
    public void getPersonByColor() throws Exception {
        when(personService.findPersonByColor("blau"))
                .thenReturn(List.of(new Person(1, "Hans", "Müller", "67742", "Lauterecken", "blau" )));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/color/blau"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Hans"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Müller"));
    }
}
