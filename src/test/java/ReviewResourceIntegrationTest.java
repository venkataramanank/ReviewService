import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.ClientResponse;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.json.JSONException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import resource.ReviewResource;
import utils.ExternalString;

import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;

public class ReviewResourceIntegrationTest {

    @ClassRule
    public static ExternalString REVIEW_1 = new ExternalString("review-01.json");

    @ClassRule
    public static ExternalString REVIEW_2 = new ExternalString("review-02.json");

    @ClassRule
    public static ExternalString REVIEW_3 = new ExternalString("review-03.json");

    @ClassRule
    public static ExternalString REVIEW_INVALID = new ExternalString("review-invalid.json");

    @ClassRule
    public static ExternalString REVIEWS = new ExternalString("reviews.json");

    private static DBCollection collection;

    private static MongoServer server;

    private static MongoClient client;

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
                                                                     .addResource(createResource())
                                                                     .build();

    private static ReviewResource createResource() {
        server = new MongoServer(new MemoryBackend());

        // bind on a random local port
        InetSocketAddress serverAddress = server.bind();

        client = new MongoClient(new ServerAddress(serverAddress));
        collection = client.getDB("commerce").getCollection("reviews");
        return new ReviewResource(collection);
    }

    @Before
    public void setup() {
        collection.drop();
    }

    @AfterClass
    public static void tearDown() {
        client.close();
        server.shutdown();
    }

    @Test
    public void getReview() throws Exception {
        addReviewToDb(REVIEW_1.toString());
        addReviewToDb(REVIEW_2.toString());
        addReviewToDb(REVIEW_3.toString());

        ClientResponse response = resources.client().resource("/reviews/P1").get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        String actual = response.getEntity(String.class);
        JSONAssert.assertEquals(REVIEWS.toString(), actual, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void getReview_MissingProduct_ReturnsNotFoundResponse() {
        ClientResponse response = resources.client().resource("/reviews/P1").get(ClientResponse.class);
        assertEquals(404, response.getStatus());
        assertEquals("No Reviews Found", response.getEntity(String.class));
    }

    @Test
    public void addReview() throws JSONException {
        ClientResponse response = resources
                .client()
                .resource("/reviews")
                .entity(REVIEW_1.toString(), javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class);
        assertEquals(200, response.getStatus());
        assertEquals("Review Added", response.getEntity(String.class));
        assertEquals(1, collection.count());
        JSONAssert.assertEquals(REVIEW_1.toString(), JSON.serialize(collection.find().next()),
                                JSONCompareMode.LENIENT);
    }

    // TODO : Fix this test
    @Ignore
    @Test
    public void addReview_InvalidInput_ReturnsBadRequestResponse() {
        ClientResponse response = resources
                .client()
                .resource("/reviews")
                .entity(REVIEW_INVALID.toString(), javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class);
        assertEquals(400, response.getStatus());
    }

    private void addReviewToDb(String reviewJson) {
        collection.insert((DBObject) JSON.parse(reviewJson));
    }
}
