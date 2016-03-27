package com.github.medhanie.spboot.ws.service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.medhanie.spboot.ws.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.medhanie.spboot.model.Person;

@Service
public class PersonServiceBean implements personInterface {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Collection<Person> findAll() {
		Collection<Person> persons = personRepository.findAll();
		return persons;
	}

	@Override
	public Person findOne(Long id) {
		Person existingPerson = personRepository.findOne(id);
		return existingPerson;
	}

	@Override
	public Person update(Person person) {
		Person personDatabase = findOne(person.getId());
		if(personDatabase == null){
			return null;
		}
		Person personSaved = personRepository.save(person);
		return personSaved;
	}

	@Override
	public void delete(Long id) {
		personRepository.delete(id);
	}

	@Override
	public Person create(Person person) {
		if(person.getId() != null){
			//not possible to create a person without Id
			return null;
		}
		Person personSaved = personRepository.save(person);
		return personSaved;
	}

}
