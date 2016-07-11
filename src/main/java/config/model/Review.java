package config.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class Review {

    private final String productId;
    private final String userId;

    @Nullable
    private final String review;

    private final double rating;


    @JsonCreator
    public Review(@JsonProperty("_id") String id,
                  @JsonProperty("productId") final String productId,
                  @JsonProperty("userId") final String userId,
                  @Nullable @JsonProperty("review") final String review,
                  @JsonProperty("rating") final Double rating) {
        this.productId = checkNotNull(productId);
        this.userId = checkNotNull(userId);
        this.review = review;
        this.rating = checkNotNull(rating);
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getReview() {
        return review;
    }

    public double getRating() {
        return rating;
    }
}
