package com.thoughtworks.uaisoccer.teams;

import com.thoughtworks.uaisoccer.common.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamStore {

    @Autowired
    SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Long create(Team team) {
        return (Long)getSession().save(team);
    }

    public Team read(Long id) throws ObjectNotFoundException {
        Team entity = (Team)getSession().get(Team.class, id);
        if (entity == null) {
            throw new ObjectNotFoundException(String.format("Could not find entity with id %d", id));
        }
        return entity;
    }
}