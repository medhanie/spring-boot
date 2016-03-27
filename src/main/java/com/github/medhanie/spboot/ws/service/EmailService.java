package com.github.medhanie.spboot.ws.service;

import com.github.medhanie.spboot.model.Person;

import java.util.concurrent.Future;

/**
 * Created by Medhanie on 3/27/2016.
 */
public interface EmailService {

    Boolean send(Person person);

    void sendAsync(Person person);

    Future<Boolean> sendAsyncWithResult(Person person);
}
