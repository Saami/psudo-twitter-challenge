package challenge.service.impl;

import challenge.dao.api.TweetDAO;
import challenge.model.Tweet;
import challenge.service.api.TweetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasiddi on 4/14/17.
 */
@Component
public class TweetServiceImpl implements TweetService {
    private static final Logger LOG = LoggerFactory.getLogger(TweetServiceImpl.class);

    @Autowired
    TweetDAO tweetDAO;

    @Override
    public List<Tweet> getTweetsForFeed(Long personId, String keyword) {
        if (personId == null) {
            throw new IllegalArgumentException("personId is required to display feed");
        }
        List<Tweet> feed = new ArrayList<Tweet>();
        feed = StringUtils.isEmpty(keyword) ? tweetDAO.getFeed(personId) : tweetDAO.getFilteredFeed(personId, keyword);
        return feed;
    }


}
