package com.github.medhanie.spboot.ws.service;

import java.math.BigInteger;
import java.util.Collection;

import com.github.medhanie.spboot.model.Person;

public interface PersonService {

	Collection<Person> findAll();

	Person findOne(Long id);

	Person update(Person person);

	Person create(Person person);

	void delete(Long id);

	void evictCache();
}
