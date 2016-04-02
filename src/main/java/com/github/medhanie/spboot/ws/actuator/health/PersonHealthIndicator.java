package com.github.medhanie.spboot.ws.actuator.health;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Collection;

/**
 * Created by Medhanie on 4/1/2016.
 */
public class PersonHealthIndicator implements HealthIndicator {

    @Autowired
    private PersonService personService;
    @Override
    public Health health() {
        Collection<Person> list = personService.findAll();
        if( list == null || list.size() == 0){
            return Health.down().withDetail("count", 0).build();
        }
        return Health.up().withDetail("count", list.size()).build();
    }
}
