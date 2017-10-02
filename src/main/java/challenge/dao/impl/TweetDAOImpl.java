package challenge.dao.impl;

import challenge.dao.api.TweetDAO;
import challenge.model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sasiddi on 4/13/17.
 */
@Repository
public class TweetDAOImpl implements TweetDAO {
    private static final Logger LOG = LoggerFactory.getLogger(TweetDAOImpl.class);

    private static final String TABLE_NAME = "TWEET";
    private static final String SPACE = " ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String feedQuery = "Select * From %s Where %s (PERSON_ID IN ( " +
            "Select * From ( " +
                "Select PERSON_ID From %s Where FOLLOWER_PERSON_ID = :personId Group By PERSON_ID) AS subquery)" +
                "Or PERSON_ID = :personId) Order By Id Desc";
    @Override
    public List<Tweet> getFeed(Long personId) {
        if (personId == null) {
            throw new IllegalArgumentException("personId is required to get feed");
        }
        List<Tweet> tweets = new ArrayList<Tweet>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", personId);
            List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(String.format(feedQuery, TABLE_NAME, SPACE, FollowerDAOImpl.TABLE_NAME), paramMap);
            tweets = getTweetObj(results);
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving feed for personId:%s", personId), e);
        }

        return tweets;
    }

    private static final String filteredFeedQuery = "CONTENT Like :keyword And";
    @Override
    public List<Tweet> getFilteredFeed(Long personId, String keyword) {
        if (personId == null || keyword == null) {
            throw new IllegalArgumentException("personId and keyword is required to get filtered feed");
        }
        List<Tweet> tweets = new ArrayList<Tweet>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", personId);
            paramMap.put("keyword", "%" + keyword + "%");
            List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(String.format(feedQuery, TABLE_NAME, filteredFeedQuery, FollowerDAOImpl.TABLE_NAME), paramMap);
            tweets = getTweetObj(results);
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving filtered feed for personId:%s and keyword:%s", personId, keyword), e);
        }

        return tweets;
    }

    private List<Tweet> getTweetObj(List<Map<String, Object>> results) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Map<String, Object> row : results) {
            Tweet tweet = new Tweet()
                    .setId(((BigDecimal) row.get("PERSON_ID")).longValue())
                    .setPersonId(((BigDecimal) row.get("PERSON_ID")).longValue())
                    .setContent((String) row.get("CONTENT"));

            tweets.add(tweet);
        }
        return tweets;
    }
}


