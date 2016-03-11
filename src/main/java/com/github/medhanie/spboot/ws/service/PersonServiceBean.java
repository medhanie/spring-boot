package com.github.medhanie.spboot.ws.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.medhanie.spboot.model.Person;

@Service
public class PersonServiceBean implements personInterface {

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

	private static boolean remove(BigInteger id) {
		return personsMap.remove(id) != null;
	}

	@Override
	public Collection<Person> findAll() {
		Collection<Person> persons = personsMap.values();
		return persons;
	}

	@Override
	public Person findOne(BigInteger id) {
		Person existingPerson = personsMap.get(id);
		return existingPerson;
	}

	@Override
	public Person update(Person person) {
		Person personSaved = save(person);
		return personSaved;
	}

	@Override
	public void delete(BigInteger id) {
		remove(id);
	}

	@Override
	public Person create(Person person) {
		Person personSaved = save(person);
		return personSaved;
	}

}
