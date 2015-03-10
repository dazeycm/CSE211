package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * parseHashtags testing partitions
 * 	A. Check if hashtags are parsed correctly
 * 		1. Single hashtag parsed correctly
 * 		2. Multiple hashtags parsed correctly
 * 		3. Check for nothing before hashtag
 * 		4. Check for only alphanumeric in hashtag
 * 		5. Check for case in-sensitity
 * 
 * hashtagMap testing partitions
 * 	A. Given 0 tweets
 * 	B. Check if hashtagMap is created correctly
 * 		0. No hashtags
 * 		1. One hashtag, one user
 * 		2. One hashtag, multiple users
 * 		3. Multiple hashtag, multiple user
 * 
 * guessFollowsGraphUsingCommonHashtags testing partitions
 * 	A. Given empty list of tweets
 * 	B. Given non-empty list
 * 		1. One shared hashtag implies mutual followship between multiple people
 * 		2. Multiple shared hashtags implies followship between multiple people
 * 		3. No shared hashtags implies no followship
 * 		4. Partially shared hashtags implies followship between some people
 */

public class MySocialNetworkTest 	{
	
	private static Tweet tweet0;
	private static Tweet tweet1;
	private static Tweet tweet2;
	private static Tweet tweet3;
	private static Tweet tweet4;
	private static Tweet tweet5;
	private static Tweet tweet6;
	private static Tweet tweet7;
	
	@BeforeClass
    public static void setUpBeforeClass() {
        Instant d1 = Instant.parse("2014-09-14T10:00:00Z");
        
        tweet0 = new Tweet(0, "craigdazey", "hashtagnohashtagshere", d1);
        tweet1 = new Tweet(0, "craigdazey", "#bluesclues", d1);
        tweet2 = new Tweet(1, "stevieyakkel", "#bluesclues #GDC", d1);
        tweet3 = new Tweet(2, "ben", "#GDC", d1);
        tweet4 = new Tweet(3, "drewclark", "#savethewhales", d1);
        tweet5 = new Tweet(4, "alyssa", "#bluesclues #savethewhales", d1);
        tweet6 = new Tweet(5, "testhashtags1", "#GDC 22#fh3hf #fj3j29 #!!!fail #GdC", d1);
        tweet7 = new Tweet(6, "damonducket", "#bluesclues", d1);
    }
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testSingleHashtagParsedCorrectly()	{
		Set<String> hashtags = SocialNetwork.parseHashtags(tweet1);
		assertTrue(hashtags.contains("bluesclues"));
	}
	
	@Test
	public void testMultipleHashtagsParsedCorrectly()	{
		Set<String> hashtags = SocialNetwork.parseHashtags(tweet2);
		assertTrue(hashtags.containsAll(Arrays.asList("bluesclues", "gdc")));
	}
	
	@Test
	public void testNothingBeforeHashtag()	{
		Set<String> hashtags = SocialNetwork.parseHashtags(tweet6);
		assertFalse(hashtags.contains("fh3hf"));
	}
	
	@Test
	public void testOnlyLettersAndNumbersInHashtag()	{
		Set<String> hashtags = SocialNetwork.parseHashtags(tweet6);
		assertFalse(hashtags.contains("!!!fail"));
	}
	
	@Test
	public void testCaseInsensitivityInHashtag()	{
		Set<String> hashtags = SocialNetwork.parseHashtags(tweet6);
		assertFalse(hashtags.contains("GdC"));
	}
	
	@Test
	public void testGiven0TweetsHashtagMapThrowsIllegalArgumentException()	{
		exception.expect(IllegalArgumentException.class);
		Map<String, Set<String>> hashtagMap = SocialNetwork.hashtagMap(Arrays.asList());
	}
	
	@Test
	public void testGivenNoHashtags()	{
		Map<String, Set<String>> hashtagMap = SocialNetwork.hashtagMap(Arrays.asList(tweet0));
		assertTrue(hashtagMap.isEmpty());
	}
	
	@Test
	public void testGivenOneHashtagOneUser()	{
		Map<String, Set<String>> hashtagMap = SocialNetwork.hashtagMap(Arrays.asList(tweet1));
		assertTrue(hashtagMap.containsKey("bluesclues"));
		assertEquals(1, hashtagMap.keySet().size());
		assertTrue(hashtagMap.get("bluesclues").contains("craigdazey"));
		assertEquals(1, hashtagMap.get("bluesclues").size());
	}
	
	@Test
	public void testGivenOneHashtagMultipleUsers()	{
		Map<String, Set<String>> hashtagMap = SocialNetwork.hashtagMap(Arrays.asList(tweet1, tweet7));
		assertTrue(hashtagMap.containsKey("bluesclues"));
		assertEquals(1, hashtagMap.keySet().size());
		assertTrue(hashtagMap.get("bluesclues").containsAll(Arrays.asList("craigdazey", "damonducket")));
	}
	
	@Test
	public void testGivenMultipleHashtagsMultipleUsers()	{
		Map<String, Set<String>> hashtagMap = SocialNetwork.hashtagMap(Arrays.asList(tweet1, tweet2, tweet3));
		assertTrue(hashtagMap.containsKey("bluesclues"));
		assertTrue(hashtagMap.containsKey("gdc"));
		assertTrue(hashtagMap.get("bluesclues").containsAll(Arrays.asList("craigdazey", "stevieyakkel")));
		assertTrue(hashtagMap.get("gdc").containsAll(Arrays.asList("stevieyakkel", "ben")));
	}
	
	@Test
	public void testGuessFollowsGraphGivenNoTweetsThrowsIllegalArgumentException()	{
		exception.expect(IllegalArgumentException.class);
		Map<String, Set<String>> followers = SocialNetwork.guessFollowsGraphUsingHashtags(Arrays.asList());
	}
	
	@Test
	public void testGuessFollowsGraphHashtagImpliesMutualFriendship()	{
		Map<String, Set<String>> followers = SocialNetwork.guessFollowsGraphUsingHashtags(Arrays.asList(tweet1, tweet7));
		assertTrue(followers.containsKey("craigdazey"));
		assertTrue(followers.containsKey("damonducket"));
		assertTrue(followers.get("craigdazey").contains("damonducket"));
		assertTrue(followers.get("damonducket").contains("craigdazey"));
	}
	
	@Test
	public void testGuessFollowsGraphHashtagMultipleHashtagsMultipleFriendships()	{
		Map<String, Set<String>> followers = SocialNetwork.guessFollowsGraphUsingHashtags(Arrays.asList(tweet1, tweet2, tweet3));
		assertTrue(followers.containsKey("craigdazey"));
		assertTrue(followers.containsKey("stevieyakkel"));
		assertTrue(followers.containsKey("ben"));
		assertTrue(followers.get("craigdazey").contains("stevieyakkel"));
		assertTrue(followers.get("stevieyakkel").contains("ben"));
	}
	
	@Test
	public void testGuessFollowsGraphHashtagNoSharedHashtagsNoFriendships()	{
		Map<String, Set<String>> followers = SocialNetwork.guessFollowsGraphUsingHashtags(Arrays.asList(tweet1, tweet3));
		assertTrue(followers.containsKey("craigdazey"));
		assertTrue(followers.containsKey("ben"));
		assertTrue(followers.get("craigdazey").isEmpty());
		assertTrue(followers.get("ben").isEmpty());
	}
	
	@Test
	public void testGuessFollowsGraphHashtagSomeSharedHashtagsSomeFriendships()	{
		Map<String, Set<String>> followers = SocialNetwork.guessFollowsGraphUsingHashtags(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));
		assertTrue(followers.containsKey("craigdazey"));
		assertTrue(followers.containsKey("stevieyakkel"));
		assertTrue(followers.containsKey("ben"));
		assertTrue(followers.containsKey("drewclark"));
		assertTrue(followers.containsKey("alyssa"));
		
		assertTrue(followers.get("craigdazey").containsAll(Arrays.asList("stevieyakkel", "alyssa")));
		assertTrue(followers.get("stevieyakkel").containsAll(Arrays.asList("craigdazey", "ben", "alyssa")));
		assertTrue(followers.get("ben").contains("stevieyakkel"));
		assertTrue(followers.get("drewclark").contains("alyssa"));
		assertTrue(followers.get("alyssa").containsAll(Arrays.asList("drewclark", "craigdazey", "stevieyakkel")));
	}
}

