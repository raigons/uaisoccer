package com.thoughtworks.uaisoccer.greetings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class })
@WebAppConfiguration()
public class GreetingControllerTest {

    // http://www.javacodegeeks.com/2013/08/unit-testing-of-spring-mvc-controllers-rest-api.html

    @Test
    public void requestGreeting() throws Exception {

    }
}