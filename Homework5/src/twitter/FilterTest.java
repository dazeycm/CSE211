/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

/*
 * Craig Dazey
 * Dr. Kiper
 * 3-10-2015
 * CSE 211
 * Followed requirements set by others to implement code, used existing specifications to implement methods, and wrote tests for methods.
 * Assignment 5
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * Partitions for writtenBy:
 * 	A. Given 0 Tweets
 * 	B. Given >0 Tweets
 * 		1. No tweets by author
 * 		2. Multiple tweets by author
 * 	C. Check if case is an issue
 * 
 * Partitions for inTimespan
 * 	A. Given 0 Tweets
 * 	B. Given >0 Tweets
 * 		1. 0 Tweets in timespan
 * 		2. Multiple tweets in timespan 
 * 	C. Inside timespan?
 *  	1. Tweet is inside timespan
 *  	2. Tweet is on start/end of timespan
 *  	3. Tweet is outside of timespan
 *  
 * Partitions for containing
 * 	A. Given 0 Tweets
 * 	B. Given >0 Tweets
 * 		1. No tweets contains words
 * 		2. Multiple Tweets contains words
 * 			a. Contains multiple words
 * 	C. Check if case is an issue
 */

public class FilterTest {

	private static Instant d1;
    private static Instant d2;
    private static Instant d3;
    private static Instant d4;
    private static Instant d5;
    private static Instant superEarly;
    private static Instant superLate;

    private static Tweet tweet1;
    private static Tweet tweet2;
    private static Tweet tweet3;
    private static Tweet tweet4;
    private static Tweet tweet6;
    private static Tweet tweet7;
    
    @BeforeClass
    public static void setUpBeforeClass() {
        d1 = Instant.parse("2014-09-14T10:00:00Z");
        d2 = Instant.parse("2014-09-14T11:00:00Z");
        d3 = Instant.parse("2014-09-15T11:00:00Z");
        d4 = Instant.parse("2014-09-16T11:00:00Z");
        d5 = Instant.parse("2014-09-17T11:00:00Z");
        superEarly = Instant.parse("2013-09-14T10:00:00Z");
        superLate = Instant.parse("2015-09-14T10:00:00Z");
        
        tweet1 = new Tweet(0, "alyssa", "is it reasonable to talk about rivest so much?", d1);
        tweet2 = new Tweet(1, "hello", "rivest talk in 30 parsecs talk #hype", d2);
        tweet3 = new Tweet(2, "world", "rivest tallkkk in 30 minutes #hype", d3);
        tweet4 = new Tweet(3, "python", "rivest TALK in 30 seconds #hype", d4);
        tweet6 = new Tweet(5, "scheme", "rivest talk in 30 years #hype", d5);
        tweet7 = new Tweet(6, "sChEmE", "rivest talk in 30 years #hype", d5);
    }
    
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testWrittenByGiven0TweetsThrowsIllegalAgrumentException()	{
    	exception.expect(IllegalArgumentException.class);
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(), "alyssa");
    }
    
    @Test
    public void testWrittenByGivenMultipleTweetsNoResults()	{
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet2), "alyssa");
    	assertTrue(writtenBy.isEmpty());
    }
    
    @Test
    public void testWrittenByGivenMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        assertFalse(writtenBy.isEmpty());
        assertEquals(1, writtenBy.size());
        assertTrue(writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByGivenMultipleTweetsMultipleResults()	{
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet6, tweet7), "scheme");
    	assertTrue(writtenBy.size() == 2);
    	assertTrue(writtenBy.containsAll(Arrays.asList(tweet6, tweet7)));
    }
    
    @Test
    public void testWrittenByMultipleTweetsSameAuthorDiffcasing()	{
    	List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet6, tweet7), "scheme");
    	assertTrue(writtenBy.size() == 2);
    	assertTrue(writtenBy.containsAll(Arrays.asList(tweet6, tweet7)));
    	
    	writtenBy = Filter.writtenBy(Arrays.asList(tweet6, tweet7), "SCHeME");
    	assertTrue(writtenBy.size() == 2);
    	assertTrue(writtenBy.containsAll(Arrays.asList(tweet6, tweet7)));
    }
    
    @Test
    public void testInTimespanGiven0TweetsThrowsIllegalArgumentException()	{
    	exception.expect(IllegalArgumentException.class);
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(superEarly, superLate));
    }
    
    @Test
    public void testInTimespanGivenMultipleTweetsNoResults()	{
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(d3, superLate));
    	assertTrue(inTimespan.isEmpty());
    }
    
    @Test
    public void testInTimespanGivenMultipleTweetsMultipleResults()	{
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(superEarly, superLate));
    	assertTrue(inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testInTimespanTweetInsideTimespan()	{
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(superEarly, superLate));
    	assertTrue(inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testInTimespanTweetsAtTimespanBorders()	{
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(tweet1.getTimestamp(), tweet2.getTimestamp()));
    	assertTrue(inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testInTimespanTweetsOutsideTimespan()	{
    	List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1), new Timespan(tweet2.getTimestamp(), superLate));
    	assertTrue(inTimespan.isEmpty());
    	assertFalse(inTimespan.contains(tweet1));
    }
    
    @Test
    public void testContainingGiven0TweetsThrowsIllegalArgumentException()	{
    	exception.expect(IllegalArgumentException.class);
    	List<Tweet> containing = Filter.containing(Arrays.asList(), Arrays.asList("talk"));
    }
    
    @Test
    public void testContainingMultipleTweetsNoResults()	{
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("purple"));
    	assertTrue(containing.isEmpty());
    }
    
    @Test
    public void testContainingGivenMultipleTweetsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk"));
        assertTrue(containing.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    @Test
    public void testContainingGivenMultipleTweetsMultipleWordsMultipleResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "rivest"));
        assertTrue(containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertFalse(containing.contains(tweet3));
    }
    
    @Test
    public void testContainingMultipleTweetsCheckCase()	{
    	List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet4), Arrays.asList("talk"));
    	assertTrue(containing.containsAll(Arrays.asList(tweet1, tweet4)));
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
