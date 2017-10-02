package challenge.dao;

import challenge.dao.api.TweetDAO;
import challenge.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sasiddi on 4/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetDAOTest {
    @Autowired
    TweetDAO tweetDAO;

    private final Long personId = 1L; //expecting this id to be in the database!!
    private final String keyword = "cursus";

    @Test
    public void getFeedTest() {
        List<Tweet> feed = tweetDAO.getFeed(personId);
        assertNotNull(feed);  //test relies for personId's feed to have tweets;
    }

    @Test
    public void getFilteredFeedTest() {
        List<Tweet> feed = tweetDAO.getFilteredFeed(personId, keyword);
        assertNotNull(feed);  //test relies for personId's feed to have tweet with the keyword above;
    }

    @Test
    public void getEmptyFeed() {
        List<Tweet> feed = tweetDAO.getFilteredFeed(personId, UUID.randomUUID().toString());
        assertNotNull(feed);
        assertEquals(feed.size(), 0);  //test relies for personId's feed to have tweet with the keyword above;
    }

}
