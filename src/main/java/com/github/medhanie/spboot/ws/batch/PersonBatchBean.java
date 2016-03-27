package com.github.medhanie.spboot.ws.batch;


import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Medhanie on 3/27/2016.
 */
@Component
public class PersonBatchBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonService personService;

    @Scheduled(cron = "0,30 * * * * *" )
    public void cronJob(){
        logger.info("> cronJob");

        //add schecduled logic here
        Collection<Person> persons= personService.findAll();
        logger.info("There are {} greetings in the data store.", persons.size());
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 15000)
    public void fixedRateJobWithInitialDelay(){
        logger.info("> fixedRateJobWithInitialDelay");

        long pause = 5000;
        long start = System.currentTimeMillis();
        do {
            if( start + pause < System.currentTimeMillis()){
                break;
            }
        } while(true);

        logger.info("Processing time was {} seconds.", pause / 1000);

        logger.info("< fixedRateJobWithInitialDelay");
    }
}
