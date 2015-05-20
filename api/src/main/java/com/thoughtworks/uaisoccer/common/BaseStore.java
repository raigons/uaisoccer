package com.thoughtworks.uaisoccer.common;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class BaseStore<E extends IdentifiedEntity> {

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
            throw new ObjectNotFoundException(String.format("Could not find object with id %d", id));
        }
        return entity;
    }

    public Long create(E entity) {
        return (Long)getSession().save(entity);
    }

    @SuppressWarnings("unchecked")
    public void update(E entity) throws ObjectNotFoundException {
        Long id = entity.getId();

        E existingEntity = (E)getSession().get(this.entityClass, id);
        if (existingEntity == null) {
            throw new ObjectNotFoundException(String.format("Could not find object with id %d", id));
        }

        getSession().merge(entity);
    }

    @SuppressWarnings("unchecked")
    public List<E> list() {
        Criteria criteria = getSession().createCriteria(entityClass);
        return (List<E>) criteria.list();
    }
}