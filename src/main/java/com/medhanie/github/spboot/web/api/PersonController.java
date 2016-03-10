package com.medhanie.github.spboot.web.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.medhanie.github.spboot.model.Person;

@RestController
public class PersonController {

	private static BigInteger nextId;
	private static Map<BigInteger, Person> personsMap;

	private static Person save(Person person) {
		if (personsMap == null) {
			personsMap = new HashMap<BigInteger, Person>();
			nextId = BigInteger.ONE;
		}
		if (person.getId() != null) {
			Person existingPerson = personsMap.get(person.getId());
			if (existingPerson == null) {
				return null;
			}
			personsMap.remove(existingPerson.getId());
			personsMap.put(person.getId(), person);
			return person;
		}
		person.setId(nextId);
		personsMap.put(person.getId(), person);
		nextId = nextId.add(BigInteger.ONE);
		return person;
	}

	static {
		Person p1 = new Person();
		p1.setFirsName("Medhanie");
		p1.setLastName("Mihreteab");
		p1.setDateOfBirth(java.sql.Date.valueOf("1990-07-04"));

		Person p2 = new Person();
		p2.setFirsName("Sessen");
		p2.setMiddleName("Berhane");
		p2.setLastName("Beraki");

		save(p1);
		save(p2);
	}

	private static boolean delete(BigInteger id) {
		return personsMap.remove(id) != null;
	}

	@RequestMapping(value = "/api/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Person>> getPersons() {

		Collection<Person> persons = personsMap.values();
		return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> getPerson(@PathVariable("id") BigInteger id) {
		Person person = personsMap.get(id);
		if (person == null) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		Person personNew = save(person);
		return new ResponseEntity<Person>(personNew, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
		Person personNew = save(person);
		if (personNew == null) {
			return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") BigInteger id, @RequestBody Person person) {
		boolean isDeleted = delete(id);
		if (isDeleted) {
			return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Person>(person, HttpStatus.NO_CONTENT);
	}
}
