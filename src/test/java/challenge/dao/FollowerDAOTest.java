package challenge.dao;

import challenge.dao.api.FollowerDAO;
import challenge.model.Follower;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sasiddi on 4/16/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowerDAOTest {

    @Autowired
    FollowerDAO followerDAO;

    private final Long personId = 1l; //expecting this id to be in the database!!

    @Test
    public void getFollowers() {
        List<Follower> followers = followerDAO.getFollowers(personId);
        assertNotNull(followers);
        assertTrue(followers.size() > 0); //relying on personId to have > 0 followers
    }

    @Test
    public void getFollowing() {
        List<Follower> following = followerDAO.getFollowing(personId);
        assertNotNull(following);
        assertTrue(following.size() > 0); //relying on personId to have to be following > 0
    }

    @Test
    public void getTopFollowersTest() {
        List<Follower> followers = followerDAO.getTopFollowers();
        assertNotNull(followers);
        assertTrue(followers.size() > 0); //relying on follower table not being empty
    }








}
