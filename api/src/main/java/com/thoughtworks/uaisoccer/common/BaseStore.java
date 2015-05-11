package com.thoughtworks.uaisoccer.common;

import com.thoughtworks.uaisoccer.championships.Championship;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

public class BaseStore<E extends IdentifiedEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<E> entityClass;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public BaseStore() {
        this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    public E read(Long id) throws ObjectNotFoundException {
        E entity = (E)getSession().get(this.entityClass, id);
        if (entity == null) {
            throw new ObjectNotFoundException(String.format("Could not find entity with id %d", id));
        }
        return entity;
    }

    public Long create(E entity) {
        Long id = (Long)getSession().save(entity);
        return id;
    }

    public void update(Championship championship) {
        getSession().update(championship);
    }
}
