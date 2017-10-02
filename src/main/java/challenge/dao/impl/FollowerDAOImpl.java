package challenge.dao.impl;

import challenge.dao.api.FollowerDAO;
import challenge.model.Follower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class FollowerDAOImpl implements FollowerDAO{
    private static final Logger LOG = LoggerFactory.getLogger(FollowerDAOImpl.class);

    static final String TABLE_NAME = "FOLLOWERS";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String followersQuery = String.format("SELECT * FROM %s WHERE PERSON_ID = :personId", TABLE_NAME);
    @Override
    public List<Follower> getFollowers(Long personId) {
        if (personId == null ) {
            throw new IllegalArgumentException("personId can not be null to retrieve followers");
        }
        List<Follower> followers = new ArrayList<Follower>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", personId);
            List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(followersQuery, paramMap);
            followers = getFollowersObject(results);
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving followers for personId:%s", personId), e);
        }

        return followers;
    }

    private static final String followingQuery = String.format("SELECT * FROM %s WHERE FOLLOWER_PERSON_ID = :personId", TABLE_NAME);
    @Override
    public List<Follower> getFollowing(Long personId) {
        if (personId == null ) {
            throw new IllegalArgumentException("personId can not be null to retrieve following");
        }
        List<Follower> following = new ArrayList<Follower>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", personId);
            List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(followingQuery, paramMap);
            following = getFollowersObject(results);
        } catch (Exception e) {
            LOG.error(String.format("Error retrieving following for personId:%s", personId), e);
        }

        return following;
    }


    private static final String topFollowersQuery = "SELECT main.person as PERSON_ID, main.follower as FOLLOWER_PERSON_ID, main.count\n" +
            "FROM (\n" +
            "       Select t1.person_id as person, t2.person_id as follower, cnt as count\n" +
            "       From followers as t1\n" +
            "       Join (\n" +
            "               Select person_id, COUNT(*) AS cnt\n" +
            "               From followers\n" +
            "               Group By person_id\n" +
            "                )  as t2 \n" +
            "       ON t1.follower_person_id = t2.person_id\n" +
            "       Group By t1.person_id, t2.person_id \n" +
            "       Order By t1.person_id, cnt desc\n" +
            "   ) as main \n" +
            "INNER JOIN (\n" +
            "    Select main2.person, main2.MAX(count) count\n" +
            "    FROM (\n" +
            "       Select t1.person_id as person, t2.person_id as follower, cnt as count\n" +
            "       From followers as t1\n" +
            "       Join (\n" +
            "               Select person_id, COUNT(*) AS cnt\n" +
            "               From followers\n" +
            "               Group By person_id\n" +
            "                )  as t2 \n" +
            "       ON t1.follower_person_id = t2.person_id\n" +
            "       Group By t1.person_id, t2.person_id \n" +
            "       Order By t1.person_id, cnt desc\n" +
            "   )  as main2\n" +
            "    GROUP BY main2.person\n" +
            ") as b ON main.person = b.person AND main.count = b.count";
    @Override
    public List<Follower> getTopFollowers() {
        List<Follower> followers = new ArrayList<Follower>();
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            List<Map<String, Object>> results = namedParameterJdbcTemplate.queryForList(topFollowersQuery, paramMap);
            followers = getFollowersObject(results);
        } catch (Exception e) {
            LOG.error("Error retrieving top followers",e);
        }
        return followers;
    }

    private static final String saveQuery = String.format("INSERT INTO %s (`person_id`, `follower_person_id`) VALUES (:personId, :followerPersonId)", TABLE_NAME);

    @Override
    public void save(Follower follower) {
        if (follower == null || follower.getPersonId() == null || follower.getFollowerPersonId() == null) {
            throw new IllegalArgumentException("follower object with parameters personId and followerPersonId are required to save object");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", follower.getPersonId());
            paramMap.put("followerPersonId", follower.getFollowerPersonId());
            namedParameterJdbcTemplate.update(saveQuery, paramMap);
            LOG.info(String.format("saved Follower with personId:%s and followerPersonId:%s", follower.getPersonId(), follower.getFollowerPersonId()));
        } catch (DataAccessException e) {
            LOG.error(String.format("Error saving Follower with personId:%s and followerPersonId:%s", follower.getPersonId(), follower.getFollowerPersonId()));
        }
    }

    private static final String deleteQuery = String.format("DELETE FROM %s WHERE PERSON_ID = :personId AND FOLLOWER_PERSON_ID = :followerPersonId", TABLE_NAME);

    @Override
    public void delete(Follower follower) {
        if (follower == null || follower.getPersonId() == null || follower.getFollowerPersonId() == null) {
            throw new IllegalArgumentException("follower object with parameters personId and followerPersonId are required to delete object");
        }

        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("personId", follower.getPersonId());
            paramMap.put("followerPersonId", follower.getFollowerPersonId());
            namedParameterJdbcTemplate.update(deleteQuery, paramMap);
            LOG.info(String.format("deleted Follower with personId:%s and followerPersonId:%s", follower.getPersonId(), follower.getFollowerPersonId()));
        } catch (DataAccessException e) {
            LOG.error(String.format("Error deleting Follower with personId:%s and followerPersonId:%s", follower.getPersonId(), follower.getFollowerPersonId()));
        }
    }

    private List<Follower> getFollowersObject( List<Map<String, Object>> results) {
        List<Follower> followers = new ArrayList<Follower>();
        for (Map<String, Object> row : results) {
            Follower follow = new Follower();
            if (row.containsKey("ID"))  follow.setId(new Long ((Integer) row.get("ID")));
            if (row.containsKey("PERSON_ID")) follow.setPersonId(((BigDecimal) row.get("PERSON_ID")).longValue());
            if (row.containsKey("FOLLOWER_PERSON_ID")) follow.setFollowerPersonId(((BigDecimal) row.get("FOLLOWER_PERSON_ID")).longValue());

            followers.add(follow);
        }
        return followers;
    }
}
