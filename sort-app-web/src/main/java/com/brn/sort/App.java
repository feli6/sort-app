package com.brn.sort;

import com.brn.sort.inject.SortAppModule;
import com.brn.sort.service.db.entity.SortResult;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class App extends Application<SortAppConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    private final HibernateBundle<SortAppConfiguration> hibernateBundle =
            new HibernateBundle<SortAppConfiguration>(SortResult.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(SortAppConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public String getName() {
        return "sort-app";
    }

    @Override
    public void initialize(Bootstrap<SortAppConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<SortAppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(SortAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(GuiceBundle.<SortAppConfiguration>builder().modules(new SortAppModule(hibernateBundle))
                .enableAutoConfig("com.brn.sort")
                .searchCommands()
                .build());


    }

    @Override
    public void run(SortAppConfiguration sortAppConfiguration, Environment environment) throws Exception {

    }
}
