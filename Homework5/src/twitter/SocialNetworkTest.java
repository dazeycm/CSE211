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

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
 * Influencers testing partitions
 * 	A. Given empty follower graph
 * 	B. Given one key in follower graph
 * 		1. Without followers
 * 		2. With followers
 * 	B. Given non-empty follower graph
 * 		1. Multiple users, no followers
 * 		2. Multiple users, multiple followers
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
	
	 private static Set<String> drewFollowers;
	 private static Set<String> brianFollowers;
	 private static Set<String> craigFollowers;
	 
	@BeforeClass
    public static void setUpBeforeClass() {
        d1 = Instant.parse("2014-09-14T10:00:00Z");
        d2 = Instant.parse("2014-09-14T11:00:00Z");
        d3 = Instant.parse("2014-09-14T12:00:00Z");
        
        tweet1 = new Tweet(0, "craigdazey", "@stevieyakkel", d1);
        tweet2 = new Tweet(1, "craigdazey", "blahblahblah", d2);
        tweet3 = new Tweet(2, "stevieyakkel", "testestestestest#GDC is THE BEE'S KNEES", d3);
        tweet4 = new Tweet(3, "drewclark", "@craigdazey is so cool but @stevieyakkel is not", d1);
        tweet5 = new Tweet(4, "craigdazey", "class was really fun today", d1);
        
        drewFollowers = new HashSet<String>();
        drewFollowers.add("damonduckett");
        drewFollowers.add("brian");
        drewFollowers.add("demonsparton117");
        
        brianFollowers = new HashSet<String>();
        brianFollowers.add("damonduckett");
        brianFollowers.add("brian");
        brianFollowers.add("demonsparton117");
        brianFollowers.add("craigdazey");
        
        craigFollowers = new HashSet<String>();
        craigFollowers.add("damonduckett");
        craigFollowers.add("brian");
        craigFollowers.add("demonsparton117");
        craigFollowers.add("craigdazey");
        craigFollowers.add("alyssa");
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
    	 Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3, tweet5));
    	 assertTrue(followsGraph.containsKey("craigdazey"));
    	 assertTrue(followsGraph.containsKey("stevieyakkel"));
    	 assertTrue(followsGraph.get("craigdazey").isEmpty());
    	 assertTrue(followsGraph.get("stevieyakkel").isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphMultipleUsersMultipleFollowers()	{
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet4));
    	assertTrue(followsGraph.containsKey("craigdazey"));
    	assertTrue(followsGraph.containsKey("drewclark"));
    	assertTrue(followsGraph.containsKey("stevieyakkel"));
    	assertTrue(followsGraph.get("craigdazey").contains("stevieyakkel"));
    	assertTrue(followsGraph.get("drewclark").contains("craigdazey"));
    	assertTrue(followsGraph.get("drewclark").contains("stevieyakkel"));
    	assertTrue(followsGraph.get("stevieyakkel").isEmpty());
    }
    
    @Test
    public void testInfluencersGivenEmptyFollowerGraphThrowsIllegalArgumentExcpetion()	{
    	exception.expect(IllegalArgumentException.class);
    	Map<String, Set<String>> followsGraph = new HashMap<>();
    	List<String> influencers = SocialNetwork.influencers(followsGraph);
    }
    
    @Test
    public void testInfluencersOneUserNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("craigdazey", new HashSet<String>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue(influencers.contains("craigdazey"));
    }
    
    @Test
    public void testInfluencersOneUserWithFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("drewclark", drewFollowers);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue(influencers.contains("drewclark"));
    }
    
    @Test
    public void testInfluencersMultipleUsersNoFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("craigdazey", new HashSet<String>());
        followsGraph.put("drewclark", new HashSet<String>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertTrue(influencers.contains("craigdazey"));
        assertTrue(influencers.contains("drewclark"));
    }
    
    @Test
    public void testInfluencersMultipleUsersMultipleFollowers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("drewclark", drewFollowers);
        followsGraph.put("brian", brianFollowers);
        followsGraph.put("craig", craigFollowers);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        assertEquals("brian", influencers.get(1));
        assertEquals("drewclark", influencers.get(2));
        assertEquals("craig",  influencers.get(0));
        assertTrue(influencers.size() == 3);
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
