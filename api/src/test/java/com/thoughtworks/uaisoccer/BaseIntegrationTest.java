package com.thoughtworks.uaisoccer;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/test-application-context.xml"
})
@DirtiesContext
public class BaseIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
