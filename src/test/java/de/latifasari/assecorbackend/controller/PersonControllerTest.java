package de.latifasari.assecorbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.latifasari.assecorbackend.model.Person;
import de.latifasari.assecorbackend.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private MockMvc mockMvc;

    // Test für GET /persons
    @Test
    public void getPersons() throws Exception {
        when(personRepository.findAll())
                .thenReturn(List.of(new Person( "Hans", "Müller", "67742", "Lauterecken", "blau" ),
                        new Person("Peter", "Peterson", "18439", "Stralsund", "grün")));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hans"))
                .andExpect(jsonPath("$[0].lastName").value("Müller"))
                .andExpect(jsonPath("$[0].zipCode").value("67742"))
                .andExpect(jsonPath("$[0].city").value("Lauterecken"))
                .andExpect(jsonPath("$[0].color").value("blau"))
                .andExpect(jsonPath("$[1].name").value("Peter"))
                .andExpect(jsonPath("$[1].lastName").value("Peterson"))
                .andExpect(jsonPath("$[1].zipCode").value("18439"))
                .andExpect(jsonPath("$[1].city").value("Stralsund"))
                .andExpect(jsonPath("$[1].color").value("grün"));
    }

    // Test für GET /persons/{id}
    @Test
    public void getPersonById() throws Exception {
        Person person = new Person( "Hans", "Müller", "67742", "Lauterecken", "blau" );
        person.setId(1L);

        given(personRepository.findById(1L)).willReturn(Optional.of(person));



        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hans"))
                .andExpect(jsonPath("$.lastName").value("Müller"))
                .andExpect(jsonPath("$.zipCode").value("67742"))
                .andExpect(jsonPath("$.city").value("Lauterecken"))
                .andExpect(jsonPath("$.color").value("blau"));
    }

    // Test für GET /persons/color/{color}
    @Test
    public void getPersonByColor() throws Exception {
        when(personRepository.findByColor("blau"))
                .thenReturn(List.of(new Person("Hans", "Müller", "67742", "Lauterecken", "blau" )));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/persons/color/blau"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hans"))
                .andExpect(jsonPath("$[0].lastName").value("Müller"))
                .andExpect(jsonPath("$[0].zipCode").value("67742"))
                .andExpect(jsonPath("$[0].city").value("Lauterecken"))
                .andExpect(jsonPath("$[0].color").value("blau"));
    }

    // Test für POST /persons
    @Test
    public void createPerson() throws Exception{
        String url = "/persons";
        Person person = new Person();
        person.setId(1L);
        person.setName("Hans");
        person.setLastname("Müller");
        person.setZipcode("67742");
        person.setCity("Lauterecken");
        person.setColor("blau");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(person);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andReturn();
    }



}
