package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*
 * parseHashtags testing partitions
 * 	A. Check if hashtags are parsed correctly
 * 		1. Single hashtag parsed correctly
 * 		2. Multiple hashtags
 * 		3. Check for nothing before hashtag
 * 		4. Check for only alphanumeric in hashtag
 * 		5. Check for case in-sensitity
 * 
 * hashtagMap testing partitions
 * 	A. Check if hashtagMap is created correctly
 * 		1. One hashtag, one user
 * 		2. One hashtag, multiple users
 * 		3. Multiple users, one hashtag
 * 		4. Multiple users, multiple hashtags
 * 
 * guessFollowsGraphUsingCommonHashtags testing partitions
 * 	A. Given empty list of tweets
 * 	B. Given non-empty list
 * 		1. One shared hashtag implies mutual followship between multiple people
 * 		2. Multiple shared hashtags impliesfollowship between multiple people
 * 		3. No shared hashtags implies no followship
 * 		4. Partially shared hashtags implies followship between some peopl
 */

public class MySocialNetworkTest 	{
	
	private static Tweet tweet1;
	private static Tweet tweet2;
	private static Tweet tweet3;
	private static Tweet tweet4;
	private static Tweet tweet5;
	private static Tweet tweet6;
	
	
	@BeforeClass
    public static void setUpBeforeClass() {
        Instant d1 = Instant.parse("2014-09-14T10:00:00Z");
        
        tweet1 = new Tweet(0, "craigdazey", "#bluesclues", d1);
        tweet2 = new Tweet(1, "stevieyakkel", "#bluesclues #GDC", d1);
        tweet4 = new Tweet(3, "drewclark", "#savethewhales", d1);
        tweet5 = new Tweet(4, "alyssa", "#bluesclues #savethewhales", d1);
        tweet3 = new Tweet(2, "ben", "#GDC", d1);
        tweet6 = new Tweet(5, "testhashtags1", "#GDC 22#fh3hf #fj3j29 #!!!fail #GdC", d1);
    }
	
	@Rule
    public ExpectedException exception = ExpectedException.none();
	
	
}
