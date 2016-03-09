package com.medhanie.github.spboot.web.api;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

	@RequestMapping(value = "/api/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Person>> getPersons() {
		
		Collection<Person> persons = personsMap.values();
		return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
	}
}
