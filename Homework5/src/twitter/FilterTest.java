/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/*
 * TODO: your testing strategies for these methods should go here
 */

public class FilterTest {

    private static Instant d1;
    private static Instant d2;
    
    private static Tweet tweet1;
    private static Tweet tweet2;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        d1 = Instant.parse("2014-09-14T10:00:00Z");
        d2 = Instant.parse("2014-09-14T11:00:00Z");
        
        tweet1 = new Tweet(0, "alyssa", "is it reasonable to talk about rivest so much?", d1);
        tweet2 = new Tweet(1, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    }

    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertFalse(writtenBy.isEmpty());
        assertEquals(1, writtenBy.size());
        assertTrue(writtenBy.contains(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testDateStart = Instant.parse("2014-09-14T09:00:00Z");
        Instant testDateEnd = Instant.parse("2014-09-14T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testDateStart, testDateEnd));
        
        assertFalse(inTimespan.isEmpty());
        assertTrue(inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse(containing.isEmpty());
        assertTrue(containing.containsAll(Arrays.asList(tweet1, tweet2)));
    }

/*
 * Warning: all the tests you write here must be runnable against any Filter class that follows
 * the spec.  It will be run against several staff implementations
 * of Filter, which will be done by overwriting (temporarily) your version of Filter
 * with the staff's version.  DO NOT strengthen the spec of Filter or its methods.
 * In particular, your test cases must not call helper methods of your own that you have
 * put in Filter, because that means you're testing a stronger spec than Filter says. 
 * If you need such helper methods, define them in a different class.  If you only need
 * them in this test class, then keep them in this test class. 
 */

}
