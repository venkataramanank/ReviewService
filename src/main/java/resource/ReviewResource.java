package resource;

import com.codahale.metrics.annotation.Timed;
import com.mongodb.DBCollection;
import config.model.Review;
import config.model.Reviews;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/reviews")
public class ReviewResource {

    private final JacksonDBCollection<Review, String> reviewsCollection;

    public ReviewResource(DBCollection reviewsCollection) {
        this.reviewsCollection = JacksonDBCollection.wrap(reviewsCollection,
                                                     Review.class,
                                                     String.class);

    }

    @GET
    @Timed
    @Path("/{id}")
    public Response reviews(@PathParam("id") String id) {
        // TODO : Add time-out to calling mongodb
        // TODO : gracefully handling exceptions, not leaking internal details
        DBCursor<Review> reviewsCursor = reviewsCollection.find().is("productId", id);
        Set<Review> reviews = new HashSet<>();
        double sum = 0;
        int count = 0;
        while (reviewsCursor.hasNext()) {
            Review review = reviewsCursor.next();
            reviews.add(review);
            sum += review.getRating();
            count++;
        }
        if (count == 0) {
            return Response.status(Response.Status.NOT_FOUND).entity("No Reviews Found").build();
        }
        double average = sum / count;
        double roundedAverage = (double) Math.round(average * 10) / 10;
        return Response.ok().entity(new Reviews(roundedAverage, reviews)).build();
    }

    @POST
    @Timed
    public Response addReview(Review review) {
        // TODO : add timeout to calling mongodb
        // TODO : gracefully handling exceptions, not leaking internal details
        // TODO : handling duplicates
        reviewsCollection.insert(review);
        return Response.ok().entity("Review Added").type(MediaType.TEXT_PLAIN).build();
    }
}
