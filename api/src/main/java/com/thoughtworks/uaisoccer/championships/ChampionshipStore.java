package com.thoughtworks.uaisoccer.championships;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ChampionshipStore {

    @Autowired
    private SessionFactory sessionFactory;

    public Long save(Championship brazilianLeague) {
        Session session = sessionFactory.openSession();
        Long id = (Long) session.save(brazilianLeague);
        session.close();
        return id;
    }

    public void update(Championship europeanLeague) {
        Session session = sessionFactory.openSession();
        session.update(europeanLeague);
        session.flush();
        session.close();
    }

    public Championship get(Long europeanLeagueId) {
        Session session = sessionFactory.openSession();
        Championship championship = (Championship) session.get(Championship.class, europeanLeagueId);
        session.close();
        return championship;
    }
}
