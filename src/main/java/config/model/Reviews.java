package config.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class Reviews {
    private final double average;
    private final Set<Review> reviews;

    @JsonCreator
    public Reviews(@JsonProperty("average") final double average,
                   @JsonProperty("reviews") final Set<Review> reviews) {
        this.average = average;
        this.reviews = reviews;
    }

    public double getAverage() {
        return average;
    }

    public Set<Review> getReviews() {
        return reviews;
    }
}
