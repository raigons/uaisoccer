package com.thoughtworks.uaisoccer.greetings;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreetingService {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Greeting greeting) {
        Session session = this.sessionFactory.openSession();
        session.save(greeting);
        session.close();
    }

    public List<Greeting> listAll() {
        Session session = this.sessionFactory.openSession();
        List<Greeting> greetingList = session.createQuery("FROM Greeting").list();
        session.close();
        return greetingList;
    }
}
