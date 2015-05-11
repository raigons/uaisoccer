package com.thoughtworks.uaisoccer.teams;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/test-application-context.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
public class TeamStoreTest {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    private TeamStore store;

    @Test
    public void shouldCreateTeam() {
        Team team = new Team("Cruzeiro Esporte Clube", "cruzeiro");
        Long id = store.create(team);

        Query query = getSession().createQuery("from Team where id = :id");
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<Team> queryResult = (List<Team>)query.list();
        assertThat(queryResult.contains(team), is(true));

        Team createdTeam = queryResult.get(queryResult.indexOf(team));
        assertThat(createdTeam, is(equalTo(team)));
    }
}