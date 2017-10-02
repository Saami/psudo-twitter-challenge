package challenge.dao.api;

import challenge.model.Follower;


import java.util.List;

/**
 * Created by sasiddi on 4/13/17.
 */
public interface FollowerDAO {
    List<Follower> getFollowers(Long personId);
    List<Follower> getFollowing(Long personId);
    List<Follower> getTopFollowers();
    void save(Follower follower);
    void delete (Follower follower);
}
