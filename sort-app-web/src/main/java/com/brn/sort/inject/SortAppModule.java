package com.brn.sort.inject;

import com.brn.sort.SortAppConfiguration;
import com.brn.sort.service.QuickSort;
import com.brn.sort.service.Sorter;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class SortAppModule extends AbstractModule {

    private final HibernateBundle hibernateBundle;

    public SortAppModule(HibernateBundle<SortAppConfiguration> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        bind(Sorter.class).to(QuickSort.class);
    }

    @Provides
    public SessionFactory provideSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}
