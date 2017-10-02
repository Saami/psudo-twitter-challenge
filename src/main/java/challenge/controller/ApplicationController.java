package challenge.controller;

import challenge.model.Follower;
import challenge.model.Person;
import challenge.model.Tweet;
import challenge.service.api.FollowerService;
import challenge.service.api.PersonService;
import challenge.service.api.TweetService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sasiddi on 4/13/17.
 */
@RestController
public class ApplicationController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);
    @Autowired
    TweetService tweetService;
    @Autowired
    FollowerService followerService;
    @Autowired
    PersonService personService;

    @RequestMapping(path = "/feed", method = RequestMethod.GET)
    String getFeed(@RequestParam(value = "search", defaultValue = "", required = false) String keyword) {
        JSONArray tweetArray = new JSONArray();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Person loggedInPerson = personService.getByUsername(auth.getName());

            if (loggedInPerson != null) {
                List<Tweet> tweets = tweetService.getTweetsForFeed(loggedInPerson.getId(), keyword);
                for (Tweet tweet : tweets) {
                    JSONObject tweetJson = new JSONObject();
                    Person person = personService.getPersonById(tweet.getPersonId());
                    tweetJson.put("userName", person.getUsername());
                    tweetJson.put("tweet", tweet.getContent());
                    tweetArray.put(tweetJson);
                }
                return tweetArray.toString();
            }
        } catch (Exception e) {
            LOG.error("error retrieving feed", e);
        }
        return tweetArray.toString();
    }

    @RequestMapping(path = "/following", method = RequestMethod.GET)
    String getFollowing() {
        JSONArray followingArray = new JSONArray();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Person loggedInPerson = personService.getByUsername(auth.getName());
            if (loggedInPerson != null) {
                List<Follower> following = followerService.getFollowing(loggedInPerson.getId());
                for (Follower follower : following) {
                    JSONObject followerObject = new JSONObject();
                    Person person = personService.getPersonById(follower.getPersonId());
                    followerObject.put("userName", person.getUsername());
                    followingArray.put(followerObject);
                }
                return followingArray.toString();
            }
        } catch (Exception e) {
            LOG.error("error retrieving following", e);
        }
        return followingArray.toString();
    }

    @RequestMapping(path = "/followers", method = RequestMethod.GET)
    String getFollowers() {
        JSONArray followerArray = new JSONArray();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Person loggedInPerson = personService.getByUsername(auth.getName());
            if (loggedInPerson != null) {
                List<Follower> followers = followerService.getFollowers(loggedInPerson.getId());
                for (Follower follower : followers) {
                    JSONObject followerObject = new JSONObject();
                    Person person = personService.getPersonById(follower.getFollowerPersonId());
                    followerObject.put("userName", person.getUsername());
                    followerArray.put(followerObject);
                }
                return followerArray.toString();
            }
        } catch (Exception e) {
            LOG.error("error retrieving followers", e);
        }
        return followerArray.toString();
    }

    @RequestMapping(path = "/follow", method = RequestMethod.POST)
    Object follow(@RequestParam(value = "username", required = true) String username) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Person loggedInPerson = personService.getByUsername(auth.getName());
            Person followPerson = personService.getByUsername(username);
            if (loggedInPerson != null && followPerson != null) {
                Follower follower = new Follower()
                        .setPersonId(followPerson.getId())
                        .setFollowerPersonId(loggedInPerson.getId());
                followerService.follow(follower);
            }
        } catch (Exception e) {
            LOG.error("Error following user:" + username, e);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/unfollow", method = RequestMethod.POST)
    Object unfollow(@RequestParam(value = "username", required = true) String username) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Person loggedInPerson = personService.getByUsername(auth.getName());
            Person unfollowPerson = personService.getByUsername(username);
            if (loggedInPerson != null && unfollowPerson != null) {
                Follower follower = new Follower().setPersonId(unfollowPerson.getId()).setFollowerPersonId(loggedInPerson.getId());
                followerService.unfollow(follower);
            }
        } catch (Exception e) {
            LOG.error("Error unfollowing user:" + username, e);
        }
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/topFollowers", method = RequestMethod.GET)
    String topFollowers() {
        JSONArray topFollowersArray = new JSONArray();
        try {
            List<Follower> topFollowers = followerService.getTopFollowers();
            Map<String, List<String>> topFollowersMap = new HashMap<>() ;
            for (Follower follower : topFollowers) {
                JSONObject topFollowerObj = new JSONObject();
                Person person = personService.getPersonById(follower.getPersonId());
                Person topFollower = personService.getPersonById(follower.getFollowerPersonId());
                topFollowerObj.put("Person", person.getName());
                topFollowerObj.put("Follower", topFollower.getName());
                topFollowersArray.put(topFollowerObj);
            }
        } catch (Exception e) {
            LOG.error("error retrieving top followers", e);
        }
        return topFollowersArray.toString();
    }

}
