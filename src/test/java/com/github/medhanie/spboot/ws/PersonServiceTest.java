package com.github.medhanie.spboot.ws;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.service.PersonService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

/**
 * Created by Medhanie on 3/27/2016.
 */
@Transactional
public class PersonServiceTest  extends AbstractTest{

    @Autowired
    private PersonService service;

    @Before
    public void setUp(){
        service.evictCache();
    }

    @After
    public void tearDown(){

    }

    @Test
    public void testFindAll(){
        Collection<Person> list = service.findAll();

        Assert.assertNotNull("failure -expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
    }

    @Test
    public void testFindOne(){
        Long id = 1L;
        Person person = service.findOne(id);
        Assert.assertNotNull("failure -expected not null", person);
        Assert.assertEquals("failure - expected id match", id, person.getId());
    }

    @Test
    public void testFindOneNotFound(){
        Long id= Long.MAX_VALUE;

        Person person = service.findOne(id);
        Assert.assertNull("failure -expected null", person);
    }

    @Test
    public void testCreate(){
        Person person = new Person();
        person.setFirstName("Abraham");
        person.setMiddleName("Samuel");
        person.setLastName("Siem");
        person.setDateOfBirth(java.sql.Date.valueOf("1980-04-09"));

        Person createdPerson = service.create(person);

        Assert.assertNotNull("failure -expected not null", createdPerson);
        Assert.assertNotNull("failure - expected id not null", createdPerson.getId());

        Assert.assertEquals("failure - expected id match", "Abraham", person.getFirstName());
        Assert.assertEquals("failure - expected id match", "Samuel", person.getMiddleName());
        Assert.assertEquals("failure - expected id match", "Siem", person.getLastName());
        Assert.assertEquals("failure - expected id match", java.sql.Date.valueOf("1980-04-09"), person.getDateOfBirth());

        Collection<Person> list = service.findAll();

        Assert.assertNotNull("failure -expected not null", list);
        Assert.assertEquals("failure - expected size", 3, list.size());
    }

    @Test
    public void testCreateWithId(){
        Exception ex = null;

        Person person = new Person();
        person.setId(Long.MAX_VALUE);
        person.setFirstName("Medhanie");
        person.setLastName("Mihreteab");

        try{
            service.create(person);
        } catch (EntityExistsException enEx){
            ex = enEx;
        }

        Assert.assertNotNull("failure - expected exception", ex);
        Assert.assertTrue("failure - expected EntityExistsException", ex instanceof EntityExistsException);
    }

    @Test
    public void testUpdate(){
        Long id = 1L;
        Person person = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", person);

        String newFirstName = "Test";
        person.setFirstName(newFirstName);

        Person updatedPerson = service.update(person);

        Assert.assertNotNull("failure - expected updated entity not null", updatedPerson);
        Assert.assertEquals("failure - expected updated entity id attribute unchanged", id, updatedPerson.getId());
        Assert.assertEquals("failure - expected updated entity First Name attribute match", newFirstName, updatedPerson.getFirstName());
    }

    @Test
    public void testUpdateNotFound(){
        Exception exception = null;

        Person person = new Person();
        person.setId(Long.MAX_VALUE);
        person.setFirstName("Yishak");
        person.setLastName("Belay");

        try{
            service.update(person);
        } catch (NoResultException nre){
            exception = nre;
        }
        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResusltException", exception instanceof NoResultException);
    }

    @Test
    public void testDelete(){
        Long id = 1L;

        Person person = service.findOne(id);
        Assert.assertNotNull("failure - expected not null", person);

        service.delete(id);

        Collection<Person> list = service.findAll();
        Assert.assertEquals("failure - expected size", 1, list.size());

        Person deletedPerson = service.findOne(id);
        Assert.assertNull("failure - expected entity to be deleted", deletedPerson);
    }
}
