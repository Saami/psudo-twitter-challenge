package challenge.service.impl;

import challenge.dao.api.FollowerDAO;
import challenge.model.Follower;
import challenge.service.api.FollowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sasiddi on 4/13/17.
 */
@Component
public class FollowerServiceImpl implements FollowerService {
    private static final Logger LOG = LoggerFactory.getLogger(FollowerServiceImpl.class);

    @Autowired
    FollowerDAO followerDAO;

    @Override
    public void follow(Follower follower) {
        if (follower == null) {
            throw new IllegalArgumentException("follower object can not be null to add a follower");
        }
        followerDAO.save(follower);
    }

    @Override
    public void unfollow(Follower follower) {
        if (follower == null) {
            throw new IllegalArgumentException("follower object can not be null to remove a follower");
        }

        followerDAO.delete(follower);
    }

    @Override
    public List<Follower> getTopFollowers() {
        return followerDAO.getTopFollowers();
    }

    @Override
    public List<Follower> getFollowers(Long personId) {
        if (personId == null) {
            throw new IllegalArgumentException("personId must be provided to get Followers");
        }

        return followerDAO.getFollowers(personId);
    }

    @Override
    public List<Follower> getFollowing(Long personId) {
        if (personId == null) {
            throw new IllegalArgumentException("personId must be provided to get following");
        }

        return followerDAO.getFollowing(personId);
    }
}
