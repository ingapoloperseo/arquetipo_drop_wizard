package net.arquetipo.base;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.*;

public class CoreAppConfiguration extends Configuration {
	@NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @NotEmpty
    private String dateFormat;

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

    @JsonProperty
    public String getDateFormat() {
        return dateFormat;
    }
}
