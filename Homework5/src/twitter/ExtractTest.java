/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Partitions for getTimeSpan:
 * 	A. # of tweets
 * 		1. Given 0 Tweets
 * 		2. Given 1 Tweet
 * 		3. Given >1 Tweet
 * 			A. Given 2 Tweets
 * 			B. Given 3 Tweets
 */

public class ExtractTest {
    
    private static Instant d1;
    private static Instant d2;
    private static Instant d3;

    
    private static Tweet tweet1;
    private static Tweet tweet2;
    private static Tweet tweet3;

    
    @BeforeClass
    public static void setUpBeforeClass() {
        d1 = Instant.parse("2014-09-14T10:00:00Z");
        d2 = Instant.parse("2014-09-14T11:00:00Z");
        d3 = Instant.parse("2014-09-14T12:00:00Z");
        
        tweet1 = new Tweet(0, "alyssa", "is it reasonable to talk about rivest so much?", d1);
        tweet2 = new Tweet(1, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
        tweet3 = new Tweet(2, "tada", "testestestestest", d3);
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        assertEquals(d1, timespan.getStart());
        assertEquals(d2, timespan.getEnd());
    }

    @Test
    public void testGetTimespanThreeTweets()	{
    	 Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3));
         assertEquals(d1, timespan.getStart());
         assertEquals(d3, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue(mentionedUsers.isEmpty());
    }

/*
 * Warning: all the tests you write here must be runnable against any Extract class that follows
 * the spec.  It will be run against several staff implementations
 * of Extract, which will be done by overwriting (temporarily) your version of Extract
 * with the staff's version.  DO NOT strengthen the spec of Extract or its methods.
 * In particular, your test cases must not call helper methods of your own that you have
 * put in Extract, because that means you're testing a stronger spec than Extract says. 
 * If you need such helper methods, define them in a different class.  If you only need
 * them in this test class, then keep them in this test class. 
 */

}
