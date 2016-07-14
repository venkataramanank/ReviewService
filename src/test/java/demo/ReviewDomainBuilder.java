package demo;

public class ReviewDomainBuilder {

    private final int id;

    public ReviewDomainBuilder(int id) {
        this.id = id;
    }

    public ReviewDomain.Builder withTestDefaults() {
        return new ReviewDomain.Builder("P" + id, "U" + id, id % 5.0)
                .withReview(id + " Star Review");
    }
}
