package config;

import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ReviewConfiguration extends Configuration {

    @Valid
    @NotNull
    MongoConfiguration mongo;

    public MongoConfiguration getMongo() {
        return mongo;
    }

}
