package demo;

public class ReviewDtoBuilder {

    private final int id;

    public ReviewDtoBuilder(int id) {
        this.id = id;
    }

    public ReviewDto.Builder withTestDefaults() {
        return new ReviewDto.Builder("P" + id, "U" + id, id % 5.0)
                .withReview(id + " Star Review");
    }
}
