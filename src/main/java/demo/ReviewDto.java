package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReviewDto {

    private final String productId;
    private final String userId;
    private final double rating;

    @Nullable
    private final String review;

    @JsonCreator
    public ReviewDto(Builder builder) {
        this.productId = builder.productId;
        this.userId = builder.userId;
        this.rating = builder.rating;
        this.review = builder.review;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewDto reviewDto = (ReviewDto) o;
        return Double.compare(reviewDto.rating, rating) == 0 &&
               Objects.equals(productId, reviewDto.productId) &&
               Objects.equals(userId, reviewDto.userId) &&
               Objects.equals(review, reviewDto.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId, rating, review);
    }

    public static class Builder {
        private final String productId;
        private final String userId;
        private final double rating;

        @Nullable
        private String review;

        public Builder(@JsonProperty("productId") final String productId,
                       @JsonProperty("userId") final String userId,
                       @JsonProperty("rating") final Double rating) {
            this.productId = checkNotNull(productId);
            this.userId = checkNotNull(userId);
            this.rating = checkNotNull(rating);
        }

        public Builder withReview(@Nullable String review) {
            this.review = review;
            return this;
        }

        public ReviewDto build() {
            return new ReviewDto(this);
        }

    }
}
