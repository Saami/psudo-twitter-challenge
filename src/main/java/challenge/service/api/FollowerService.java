package challenge.service.api;

import challenge.model.Follower;


import java.util.List;

/**
 * Created by sasiddi on 4/13/17.
 */
public interface FollowerService {
    void follow(Follower follower);
    void unfollow(Follower follower);
    List<Follower> getTopFollowers();
    List<Follower> getFollowers(Long personId);
    List<Follower> getFollowing(Long personId);
}
