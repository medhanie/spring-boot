package com.github.medhanie.spboot.ws.service;

import java.util.Collection;

import com.github.medhanie.spboot.ws.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.medhanie.spboot.model.Person;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PersonServiceBean implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CounterService counterService;

	@Override
	public Collection<Person> findAll() {
		counterService.increment("Method.invoked.personService.findAll");
		Collection<Person> persons = personRepository.findAll();
		return persons;
	}

	@Override
	@Cacheable(value = "persons", key="#id")
	public Person findOne(Long id) {
		counterService.increment("Method.invoked.personService.findOne");
		Person existingPerson = personRepository.findOne(id);
		return existingPerson;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "persons", key="#person.id")
	public Person update(Person person) {
		counterService.increment("Method.invoked.personService.update");
		Person personDatabase = findOne(person.getId());
		if(personDatabase == null){
			return null;
		}
		Person personSaved = personRepository.save(person);
		return personSaved;
	}

	@Override
	@CacheEvict(value="persons", key="#id")
	public void delete(Long id) {
		counterService.increment("Method.invoked.personService.delete");
		personRepository.delete(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@CachePut(value = "persons", key="#result.id")
	public Person create(Person person) {
		counterService.increment("Method.invoked.personService.create");
		if(person.getId() != null){
			//not possible to create a person without Id
			return null;
		}
		Person personSaved = personRepository.save(person);
		return personSaved;
	}

	@Override
	@CacheEvict(value = "persons", allEntries = true)
	public void evictCache(){
		counterService.increment("Method.invoked.personService.evictCache");

	}
}
