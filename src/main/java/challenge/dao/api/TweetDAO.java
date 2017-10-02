package challenge.dao.api;

import challenge.model.Tweet;


import java.util.List;

/**
 * Created by sasiddi on 4/13/17.
 */
public interface TweetDAO {
    List<Tweet> getFeed(Long personId);
    List<Tweet> getFilteredFeed(Long personId, String keyword);

}
