package net.arquetipo.base;

import net.arquetipo.base.health.TemplateHealthCheck;
import net.arquetipo.base.resources.HelloWorldResource;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class CoreAppConfiguration extends Configuration {
	@NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }
    
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
    }
}
