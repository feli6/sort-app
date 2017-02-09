package com.brn.sort.service.db.entity;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class SortResultDao extends AbstractDAO<SortResult> {

    @Inject
    public SortResultDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public SortResult create(SortResult sortResult) {
        return persist(sortResult);
    }

    public List<SortResult> findAll() {
        //TODO:pagination can be used later on
        return list(namedQuery("com.brn.sort.service.db.entity.SortResult.findAll"));
    }
}
