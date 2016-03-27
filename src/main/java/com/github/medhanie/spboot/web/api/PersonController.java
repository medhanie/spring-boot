package com.github.medhanie.spboot.web.api;

import java.util.Collection;
import java.util.concurrent.Future;

import com.github.medhanie.spboot.ws.service.EmailService;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.service.PersonService;

@RestController
public class PersonController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonService personService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/api/persons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Person>> getPersons() {

		Collection<Person> persons = personService.findAll();
		return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/persons/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> getPerson(@PathVariable("id") Long id) {
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
	public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id, @RequestBody Person person) {
		personService.delete(id);
		return new ResponseEntity<Person>(person, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value="/api/persons/{id}/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Person> sendEmailPerrson(@PathVariable("id") Long id, @RequestParam(value="wait", defaultValue = "false") boolean waitForAsyncResult){
		logger.info("< sendEmailPerson");

		Person person = null;

		try{
			person = personService.findOne(id);
			if(person == null){
				logger.info("< sendEmailPerson");
				return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
			}

			if(waitForAsyncResult){
				Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(person);
				boolean emailSent = asyncResponse.get();
				logger.info(" - greeting eamil sent? {}", emailSent);
			}else{
				emailService.sendAsync(person);
			}
		}catch (Exception ex){
			logger.error("A problem occured sending the Person Email.", ex);
			return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("< sendGreeting");
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
}
