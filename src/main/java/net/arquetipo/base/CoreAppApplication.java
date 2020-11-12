//https://www.dropwizard.io/en/latest/getting-started.html

package net.arquetipo.base;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.arquetipo.base.db.UserDAO;
import net.arquetipo.base.health.TemplateHealthCheck;
import net.arquetipo.base.resources.HelloWorldResource;
import net.arquetipo.base.resources.UserResource;
import org.skife.jdbi.v2.DBI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CoreAppApplication extends Application<CoreAppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new CoreAppApplication().run(args);
    }

    @Override
    public String getName() {
        return "CoreApp";
    }

    @Override
    public void initialize(final Bootstrap<CoreAppConfiguration> bootstrap) {

        // https://www.dropwizard.io/en/latest/manual/migrations.html
        bootstrap.addBundle(new MigrationsBundle<CoreAppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(CoreAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

    }

    @Override
    public void run(CoreAppConfiguration configuration, Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

        // User resource
        final UserDAO dao = jdbi.onDemand(UserDAO.class);
        environment.jersey().register(new UserResource(dao));

        // Formato de fecha para la aplicación
        DateFormat eventDateFormat = new SimpleDateFormat(configuration.getDateFormat());
        environment.getObjectMapper().setDateFormat(eventDateFormat);
    }
}
