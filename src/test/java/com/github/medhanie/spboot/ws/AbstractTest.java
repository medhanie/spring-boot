package com.github.medhanie.spboot.ws;

import com.github.medhanie.spboot.Application;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Medhanie on 3/27/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AbstractTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
