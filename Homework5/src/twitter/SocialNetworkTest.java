/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/*
 * TODO: your testing strategies for these methods should go here
 */

public class SocialNetworkTest {

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue(followsGraph.isEmpty());
    }
    
    @Test
    public void testInfluencersEmpty() {
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
