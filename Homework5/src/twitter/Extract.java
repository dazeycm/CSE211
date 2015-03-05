/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but you should implement their
 * method bodies, and you may add new public or private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	if(tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to getTimespan method");
    	Instant first = tweets.get(0).getTimestamp();
    	Instant last = tweets.get(tweets.size() - 1).getTimestamp();
    	for(Tweet tweet : tweets)	{
    		if(tweet.getTimestamp().isBefore(first))	{
    			first = tweet.getTimestamp();
    		} else if (tweet.getTimestamp().isAfter(last))	{
    			last = tweet.getTimestamp();
    		}
    	}
    	return new Timespan(first, last);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a username. A username
     *         consists of letters (A-Z or a-z), digits, and underscores ("_").
     *         Twitter usernames are case-insensitive, so "rbmllr" and "RbMllr"
     *         are equivalent. A username may occur at most once in the returned
     *         set.
     *         The @ cannot be immediately preceded by an alphanumeric or
     *         underscore character (A-Z, a-z, 0-9, _). For example, an email
     *         address like bitdiddle@mit.edu does not contain a mention of mit.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if(tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to getMentionedUsers method");
        Set<String> mentionedUsers = new HashSet<String>();
        for(Tweet tweet : tweets)	{
        	List<String> words = Arrays.asList(tweet.getText().split(" "));
        	for(String word: words)	{
        		word = word.toLowerCase();
        		if(word.matches("^@[a-z0-9_]+$"))	{
        			System.out.println("worked");
        			mentionedUsers.add(word.substring(1));
        		}
        	}
        }
        return mentionedUsers;
    }

}
