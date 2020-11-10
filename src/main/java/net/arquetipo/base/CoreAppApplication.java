package net.arquetipo.base;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    public void run(final CoreAppConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
