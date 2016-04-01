package com.github.medhanie.spboot.web.api;


import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Medhanie on 3/30/2016.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Exception> handleNoResultException(
            NoResultException nre) {
        logger.error("> handleNoResultException");
        logger.error("- NoResultException: ", nre);
        logger.error("< handleNoResultException");
        return new ResponseEntity<Exception>(HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        logger.error("> handleException");
        logger.error("- Exception: ", e);
        logger.error("< handleException");
        return new ResponseEntity<Exception>(e,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}