package demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTransformerTest {

    private ReviewTransformer reviewTransformer;

    @Before
    public void setUp() throws Exception {
        reviewTransformer = new ReviewTransformer();
    }

    @Test
    public void testTransform() {
        ReviewDto input = new ReviewDtoBuilder(1).withTestDefaults().build();
        ReviewDomain actual = reviewTransformer.transform(input);
        ReviewDomain expected = new ReviewDomainBuilder(1).withTestDefaults().build();
        assertEquals(expected, actual);
    }

    @Test
    public void testTransform_nullInput_nullOutput() {
        assertNull(reviewTransformer.transform(null));
    }
}
