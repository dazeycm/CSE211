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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * Partitions for getTimeSpan:
 * 	A. # of tweets
 * 		1. Given 0 Tweets
 * 		2. Given 1 Tweet
 * 		3. Given >1 Tweet
 * 			A. Given 2 Tweets
 * 			B. Given 3 Tweets
 * 
 * Partitions for getMentionedUsers
 * 	A. Valid mention or not
 * 		1. Test for valid mention
 * 		2. Test for two of the same name in different cases
 * 		3. Test for character before @ sign
 * 		4. Test for invalid chars in mention
 * 		5. Test for multiple mentions in one tweet
 *  B. Get mentions from different number of tweets
 *  	1. Given 0 Tweets
 *  	2. Given 1 Tweet
 *  	3. Given 2 Tweets
 */

public class ExtractTest {
    
    private static Instant d1;
    private static Instant d2;
    private static Instant d3;

    private static Tweet tweet1;
    private static Tweet tweet2;
    private static Tweet tweet3;
    private static Tweet tweet4;
    private static Tweet tweet5;
    private static Tweet tweet6;
    private static Tweet tweet7;
    private static Tweet tweet8;
    private static Tweet tweet9;
    
    
    @BeforeClass
    public static void setUpBeforeClass() {
        d1 = Instant.parse("2014-09-14T10:00:00Z");
        d2 = Instant.parse("2014-09-14T11:00:00Z");
        d3 = Instant.parse("2014-09-14T12:00:00Z");
        
        tweet1 = new Tweet(0, "alyssa", "is it reasonable to talk about rivest so much?", d1);
        tweet2 = new Tweet(1, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
        tweet3 = new Tweet(2, "tada", "testestestestest", d3);
        
        tweet4 = new Tweet(3, "alyssa", "@craigdazey is so cool", d1);
        tweet5 = new Tweet(4, "alyssa", "@CRAIGDAZEY is equally cool", d1);
        tweet6 = new Tweet(5, "alyssa", "_@craigdazey", d1);
        tweet7 = new Tweet(6, "alyssa", "@craig!!!dazey", d1);
        tweet8 = new Tweet(7, "alyssa", "@craigdazey @stevieyakkel", d1);
        tweet9 = new Tweet(8, "alyssa", "hello world said @drewclark", d1); 
    }
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testGetTimespanZeroTweetsThrowsIllegalArgumentException()	{
    	exception.expect(IllegalArgumentException.class);
    	Timespan timespan = Extract.getTimespan(Arrays.asList());
    }
    
    @Test
    public void testGetTimespanOneTweet()	{
    	Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
    	assertEquals(timespan.getEnd(), timespan.getStart());
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
