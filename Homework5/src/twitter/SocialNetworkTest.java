/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * GuessFollowsGraph testing partitions
 * 	A. Given 0 Tweets
 * 	B. Given >0 Tweets
 * 		1. Given Multiple User
 * 			a. 0 followers
 * 			b. >0 followers
 * 	
 */

public class SocialNetworkTest {

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
        
        tweet1 = new Tweet(0, "craigdazey", "@stevieyakkel", d1);
        tweet2 = new Tweet(1, "craigdazey", "blahblahblah", d2);
        tweet3 = new Tweet(2, "stevieyakkel", "testestestestest#GDC is THE BEE'S KNEES", d3);
        tweet4 = new Tweet(3, "drewclark", "@craigdazey is so cool but @stevieyakkel is not", d1);
    }
	
	@Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testGuessFollowsGraphNoTweetsThrowsIllegalArgumentException() {
    	exception.expect(IllegalArgumentException.class);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
    }
    
    @Test
    public void testGuessFollowsGraphMultipleUsersNoFollowers()	{
    	 Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList());
    }
    
    @Test
    public void testInfluencersMultipleTweetsNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue(influencers.isEmpty());
    }

/*
 * Warning: all the tests you write here must be runnable against any SocialNetwork class that follows
 * the spec.  It will be run against several staff implementations
 * of SocialNetwork, which will be done by overwriting (temporarily) your version of SocialNetwork
 * with the staff's version.  DO NOT strengthen the spec of SocialNetwork or its methods.
 * In particular, your test cases must not call helper methods of your own that you have
 * put in SocialNetwork, because that means you're testing a stronger spec than SocialNetwork says. 
 * If you need such helper methods, define them in a different class.  If you only need
 * them in this test class, then keep them in this test class. 
 */

}
