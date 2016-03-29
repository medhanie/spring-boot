package com.github.medhanie.spboot.ws.web.api;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.AbstractControllerTest;
import com.github.medhanie.spboot.ws.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Medhanie on 3/28/2016.
 */
@Transactional
public class PersonControllerTest  extends AbstractControllerTest{

    @Autowired
    private PersonService personService;

    @Before
    public void setUp(){
        super.setUp();
        personService.evictCache();
    }

    @Test
    public void testGetPersons() throws Exception {
        String uri = "/api/persons";

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", ! content.trim().isEmpty());
    }


    @Test
    public void testCreatePerson() throws Exception{
        String uri = "/api/persons";
        Person person = new Person();
        person.setFirstName("test");
        String inputJson = super.mapToJson(person);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", ! content.trim().isEmpty());

        Person createdPerson = super.mapFromJson(content, Person.class);

        Assert.assertNotNull("failure - expected person not null", createdPerson);
        Assert.assertNotNull("failure - expected person.id not null", createdPerson.getId());
        Assert.assertEquals("failure - expected person.firsName", "test", createdPerson.getFirstName());
    }

    @Test
    public void testUpdatedPerson() throws Exception{
        String uri = "/api/persons/{id}";
        Long id = 1L;
        Person person = personService.findOne(id);
        String updatedFirstName = "first Name Test";
        person.setFirstName(updatedFirstName);
        String inputJson = super.mapToJson(person);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", ! content.trim().isEmpty());

        Person updatedPerson = super.mapFromJson(content, Person.class);

        Assert.assertNotNull("failure - expected person not null", updatedPerson);
        Assert.assertEquals("failure - expected person id unchanged", person.getId(), updatedPerson.getId());
        Assert.assertEquals("failure - expected updated person First Name match", updatedFirstName, person.getFirstName());
    }

    @Test
    public void testGetPerson() throws Exception{
        String uri = "/api/persons/{id}";
        Long id =1L;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", ! content.trim().isEmpty());
    }

    @Test
    public void testGetPersonNotFound() throws Exception{
        String uri = "/api/persons/{id}";
        Long id = Long.MAX_VALUE;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().isEmpty());
    }

    @Test
    public void testDeletePerson() throws Exception {

        String uri = "/api/persons/{id}";
        Long id = 1L;

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", content.trim().isEmpty());

        Person deletedPerson = personService.findOne(id);
        Assert.assertNull("failure - expected person to be null", deletedPerson);
    }
}
