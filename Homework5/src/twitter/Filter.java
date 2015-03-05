/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but you should implement their
 * method bodies, and you may add new public or private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets, not modified by this method.
     * @param username
     *            Twitter username
     * @return all tweets in the list whose author is username. Twitter
     *         usernames are case-insensitive, so "rbmllr" and "RbMllr" are
     *         equivalent.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
    	if(tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to writtenBy method");
        List<Tweet> tweetsByUsername = new ArrayList<Tweet>();
        username = username.toLowerCase();
        
        for(Tweet tweet : tweets)	{
        	if(tweet.getAuthor().toLowerCase().equals(username))	{
        		tweetsByUsername.add(tweet);
        	}
        }
        
        return tweetsByUsername;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets, not modified by this method.
     * @param timespan
     *            timespan
     * @return all tweets in the list that were sent during the timespan.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
    	if(tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to inTimespan method");
        List<Tweet> tweetsInTimespan = new ArrayList<Tweet>();
        
        for(Tweet tweet : tweets)	{
        	if(tweet.getTimestamp().isAfter(timespan.getStart().minusSeconds(1)) && tweet.getTimestamp().isBefore(timespan.getEnd().plusSeconds(1)))	{
        		tweetsInTimespan.add(tweet);
        	}
        }
        
        return tweetsInTimespan;
    }

    /**
     * Search for tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. Words must not
     *            contain spaces.
     * @return all tweets in the list such that the tweet text (when represented
     *         as a sequence of words bounded by space characters and the ends
     *         of the string) includes *all* the words found in the words
     *         list, in any order. Word comparison is not case-sensitive, so
     *         "Obama" is the same as "obama".
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
    	if(tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to containing method");
    	List<Tweet> tweetsContainingWords = new ArrayList<Tweet>();
    	
    	for(int i = 0; i < words.size(); i++) {
    		words.set(i, words.get(i).toLowerCase());
    		System.out.println(words.get(i));
		}

    	for(Tweet tweet : tweets)	{
    		List<String> tweetWords = Arrays.asList(tweet.getText().split(" "));

    		for(int i = 0; i < tweetWords.size(); i++) {
    			tweetWords.set(i, tweetWords.get(i).toLowerCase());
    			System.out.println(tweetWords.get(i));
    		}
    		
    		if(tweetWords.containsAll(words))	{
    			tweetsContainingWords.add(tweet);
    		}
    	}
    	
    	return tweetsContainingWords;
    }

}
