package com.github.medhanie.spboot.web.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.service.personInterface;

@RestController
public class PersonController {

	@Autowired
	private personInterface personService;

	@RequestMapping(value = "/api/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Person>> getPersons() {

		Collection<Person> persons = personService.findAll();
		return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> getPerson(@PathVariable("id") BigInteger id) {
		Person person = personService.findOne(id);
		if (person == null) {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> createPerson(@RequestBody Person person) {
		Person personNew = personService.create(person);
		return new ResponseEntity<Person>(personNew, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
		Person personNew = personService.update(person);
		if (personNew == null) {
			return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") BigInteger id, @RequestBody Person person) {
		personService.delete(id);
		return new ResponseEntity<Person>(person, HttpStatus.NO_CONTENT);
	}
}
