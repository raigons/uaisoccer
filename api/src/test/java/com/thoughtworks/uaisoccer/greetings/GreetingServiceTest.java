package com.thoughtworks.uaisoccer.greetings;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/test-application-context.xml"})
@WebAppConfiguration
public class GreetingServiceTest {

    @Autowired
    private GreetingService service;

    @Test
    public void shouldSaveGreeting() {
        Greeting greeting = new Greeting("Greeting!");
        service.save(greeting);
        assertThat(service.listAll().isEmpty(), is(false));
        assertThat((service.listAll().get(0)).getContent(), is(greeting.getContent()));
    }
}