package com.github.medhanie.spboot.ws.web.api;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.web.api.PersonController;
import com.github.medhanie.spboot.ws.AbstractControllerTest;
import com.github.medhanie.spboot.ws.service.EmailService;
import com.github.medhanie.spboot.ws.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Medhanie on 3/30/2016.
 */
@Transactional
public class PersonControllerMocksTest extends AbstractControllerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUp(personController);
    }

    private Collection<Person> getEntityListStubData() {
        Collection<Person> list = new ArrayList<Person>();
        list.add(getEntityStubData());
        return list;
    }

    private Person getEntityStubData() {
        Person person = new Person();
        person.setFirstName("Ezekiel");
        person.setMiddleName("Finan");
        person.setLastName("Gorbab");
        person.setDateOfBirth(Date.valueOf("2000-09-17"));
        return person;
    }

    @Test
    public void testPerson() throws Exception {

        Collection<Person> list = getEntityListStubData();

        when(personService.findAll()).thenReturn(list);

        String uri = "/api/persons/";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(personService, times(1)).findAll();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());
    }

    @Test
    public void testGetPerson() throws Exception {
        Long id = 1L;
        Person person = getEntityStubData();

        when(personService.findOne(id)).thenReturn(person);

        String uri = "/api/persons/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(personService, times(1)).findOne(id);

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());
    }

    @Test
    public void testGetPersonNotFound() throws Exception {
        Long id = Long.MAX_VALUE;

        when(personService.findOne(id)).thenReturn(null);

        String uri = "api/persons/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        verify(personService, times(1)).findOne(id);

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTPP response body to be empty", content.trim().isEmpty());
    }

    @Test
    public void testCreateGreeting() throws Exception {
        Person person = getEntityStubData();

        when(personService.create(any(Person.class))).thenReturn(person);

        String uri = "/api/persons";
        String inputJson = super.mapToJson(person);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(personService, times(1)).create(any(Person.class));

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());

        Person createdPerson = super.mapFromJson(content, Person.class);

        Assert.assertNotNull("failure - expected perosn not null", createdPerson);
        Assert.assertNotNull("falure - expected id attribute not null", createdPerson.getId());
        Assert.assertEquals("failure - expected firs name match", person.getFirstName(), createdPerson.getFirstName());

    }

    @Test
    public void testDeletePerson() throws Exception {
        Long id = 1L;

        String uri = "/api/persons/{id}";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(personService, times(1)).delete(id);

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);
    }


    @Test
    public void testUpdatePerson() throws Exception {
        Person person = getEntityStubData();
        person.setFirstName("Test Name");
        Long id = 1L;

        when(personService.update(any(Person.class))).thenReturn(person);

        String uri = "/api/persons/{id}";
        String inputJson = super.mapToJson(person);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(personService, times(1)).update(any(Person.class));

        Assert.assertEquals("failure - expected HTTP Status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !content.trim().isEmpty());

        Person updatedPerson = super.mapFromJson(content, Person.class);

        Assert.assertNotNull("failure - expected entity not null", updatedPerson);
        Assert.assertEquals("failure - expected id attribute unchanged", person.getId(), updatedPerson.getId());
        Assert.assertEquals("failure - expected text attribute match", person.getFirstName(), updatedPerson.getFirstName());
    }
}
