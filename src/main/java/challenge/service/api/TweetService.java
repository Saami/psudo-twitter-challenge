package challenge.service.api;

import challenge.model.Tweet;


import java.util.List;

/**
 * Created by sasiddi on 4/14/17.
 */
public interface TweetService {
    List<Tweet> getTweetsForFeed(Long personId, String keyword);

}
