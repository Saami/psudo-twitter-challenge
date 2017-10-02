package challenge.model;



/**
 * Created by sasiddi on 4/13/17.
 */
public class Follower {

    private Long id;
    private Long personId;
    private Long followerPersonId;

    public Long getId() {
        return id;
    }

    public Follower setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPersonId() {
        return personId;
    }

    public Follower setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public Long getFollowerPersonId() {
        return followerPersonId;
    }

    public Follower setFollowerPersonId(Long followerPersonId) {
        this.followerPersonId = followerPersonId;
        return this;
    }
}
