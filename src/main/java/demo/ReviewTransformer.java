package demo;

import javax.annotation.Nullable;

public class ReviewTransformer {

    @Nullable
    public ReviewDomain transform(@Nullable ReviewDto reviewDto) {
        if (reviewDto == null) {
            return null;
        }
        return new ReviewDomain.Builder(reviewDto.getProductId(), reviewDto.getUserId(), reviewDto.getRating())
                .withReview(reviewDto.getReview())
                .build();
    }
}
