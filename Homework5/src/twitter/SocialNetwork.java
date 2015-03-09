/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.xml.internal.txw2.IllegalAnnotationException;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames.  Users can't follow themselves.
 * If A doesn't follow anybody, then map[A] may be undefined or map[A] may be the
 * empty set; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".  A
 * username should appear at most once as a key in the map or in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but you should implement their
 * method bodies, and you may add new public or private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be 
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	if (tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to guessFollowsGraph method");
    	Map<String, Set<String>> followers = new HashMap<String, Set<String>>();
    	
    	for(Tweet tweet : tweets)	{
    		Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet));
    		
    		if(!followers.containsKey(tweet.getAuthor()))
    			followers.put(tweet.getAuthor(), new HashSet<String>());
    		
    		for(String mentionedUser : mentionedUsers)	{
    			if(!followers.containsKey(mentionedUser))
    				followers.put(mentionedUser, new HashSet<String>());
    		}
    		
    		followers.get(tweet.getAuthor()).addAll(mentionedUsers);		
    	}
    	
    	return followers;
    }
    
    public static Map<String, Set<String>> guessFollowsGraphUsingHashtags(List<Tweet> tweets) {
    	if (tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to guessFollowsGraph method");
    	Map<String, Set<String>> followers = new HashMap<String, Set<String>>();
    	Map<String, Set<String>> hashtagMap = hashtagMap(tweets);
    	for(Entry<String, Set<String>> entry : hashtagMap.entrySet())	{
    		for(String user : entry.getValue())	{
    			if(!followers.containsKey(user))	
    				followers.put(user, new HashSet<String>());
    			followers.get(user).addAll(entry.getValue());
    			followers.get(user).remove(user);
    		}
    	}
    	
    	return followers;
    }
    
    public static Map<String, Set<String>> hashtagMap(List<Tweet> tweets){
    	if (tweets.isEmpty()) throw new IllegalArgumentException("Passed no tweets to hashtagMap method");
    	HashMap<String, Set<String>> hashtagMap = new HashMap<String, Set<String>>();
    	
    	for(Tweet tweet: tweets)	{
    		Set<String> hashtags = parseHashtags(tweet);
    		
    		for(String hashtag : hashtags)	{
    			if(!hashtagMap.containsKey(hashtag))
    				hashtagMap.put(hashtag, new HashSet<String>());
    			
    			hashtagMap.get(hashtag).add(tweet.getAuthor());
    		}
    	}
    	
    	return hashtagMap;
    }
    
    public static Set<String> parseHashtags(Tweet tweet){
    	Set<String> hashtags = new HashSet<String>();
    	List<String> words = Arrays.asList(tweet.getText().split(" "));
    	
    	for(String word: words)	{
    		word = word.toLowerCase();
    		if(word.matches("^#[a-z0-9]+$"))
    			hashtags.add(word.substring(1));
    	}
    	
		return hashtags;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	if(followsGraph.isEmpty()) throw new IllegalArgumentException("Passed no tweets to influencers method");
    	List<String> sortedKeys = new ArrayList<String>(followsGraph.keySet());
    	Collections.sort(sortedKeys, new Comparator<String>()	{
    		public int compare(String x, String y)	{
    			return followsGraph.get(y).size() - followsGraph.get(x).size();
    		}
    	});
    	
    	return sortedKeys;
    }
}
