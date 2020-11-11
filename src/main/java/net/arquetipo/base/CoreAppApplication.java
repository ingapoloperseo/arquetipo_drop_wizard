//https://www.dropwizard.io/en/latest/getting-started.html

package net.arquetipo.base;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.arquetipo.base.health.TemplateHealthCheck;
import net.arquetipo.base.resources.HelloWorldResource;

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
        // TODO: application initialization
    }  
    
    @Override
    public void run(CoreAppConfiguration configuration,
            Environment environment) {
		final HelloWorldResource resource = new HelloWorldResource(
		    configuration.getTemplate(),
		    configuration.getDefaultName()
		);
		final TemplateHealthCheck healthCheck =
		        new TemplateHealthCheck(configuration.getTemplate());
		    environment.healthChecks().register("template", healthCheck);
		
		environment.jersey().register(resource);

		// Formato de fecha para la aplicación
        DateFormat eventDateFormat = new SimpleDateFormat(configuration.getDateFormat());
        environment.getObjectMapper().setDateFormat(eventDateFormat);
	}
}
