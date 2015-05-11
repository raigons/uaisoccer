package com.thoughtworks.uaisoccer.championships;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChampionshipStore {

    @Autowired
    private SessionFactory sessionFactory;

    public Long save(Championship brasilianLeague) {
        Session session = sessionFactory.openSession();
        Long id = (Long) session.save(brasilianLeague);
        session.close();
        return id;
    }
}
