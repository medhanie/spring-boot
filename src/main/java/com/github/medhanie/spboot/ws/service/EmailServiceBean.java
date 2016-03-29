package com.github.medhanie.spboot.ws.service;

import com.github.medhanie.spboot.model.Person;
import com.github.medhanie.spboot.ws.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Medhanie on 3/27/2016.
 */
@Component
public class EmailServiceBean implements EmailService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Boolean send(Person person) {
        logger.info("> send");

        Boolean success = Boolean.TRUE;

        //Simulate method execution time
        long pause = 5000;
        try{
            Thread.sleep(pause);
        } catch(Exception ex){
            //do something
            success = Boolean.FALSE;
        }

        logger.info("Processing tim was {} seconds.", pause/1000);

        success = Boolean.TRUE;

        logger.info("< end");
        return success;
    }

    @Override
    @Async
    public void sendAsync(Person person) {
        logger.info("> sendAsync");

        try{
            send(person);
        } catch(Exception ex){
            logger.warn("Exception caught sending asynchronous mail.", ex);
        }

        logger.info("< sendAsync");
    }

    @Override
    @Async
    public Future<Boolean> sendAsyncWithResult(Person person) {
       logger.info("> sendAsyncWithResult");

        AsyncResponse<Boolean> response = new AsyncResponse<Boolean>();

        try{
            Boolean success = send(person);
            response.complete(success);
        } catch (Exception ex){
            logger.warn("Exception caught sending asynchronous mail.", ex);
            response.completeExceptionally(ex);
        }

        logger.info("< sendAsyncWithResult");
        return response;
    }
}
