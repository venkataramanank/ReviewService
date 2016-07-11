import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.meltmedia.dropwizard.mongo.MongoHealthCheck;
import com.mongodb.DB;
import config.ReviewConfiguration;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import resource.ReviewResource;

public class ReviewApplication extends Application<ReviewConfiguration> {

    MongoBundle<ReviewConfiguration> mongoBundle;

    public static void main(String[] args) throws Exception {
        new ReviewApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<ReviewConfiguration> bootstrap) {
        bootstrap.addBundle(mongoBundle = MongoBundle.<ReviewConfiguration>builder()
                .withConfiguration(ReviewConfiguration::getMongo)
                .build());
    }

    @Override
    public void run(final ReviewConfiguration configuration, final Environment environment) throws Exception {
        DB db = mongoBundle.getDB();
        environment.healthChecks().register("mongo", new MongoHealthCheck(db));
        ReviewResource reviewResource = new ReviewResource(db.getCollection("reviews"));
        environment.jersey().register(reviewResource);
    }
}
