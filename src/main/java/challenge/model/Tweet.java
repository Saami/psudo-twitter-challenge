package challenge.model;



/**
 * Created by sasiddi on 4/13/17.
 */
public class Tweet {

    private Long id;
    private Long personId;
    private String content;


    public Long getId() {
        return id;
    }

    public Tweet setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPersonId() {
        return personId;
    }

    public Tweet setPersonId(Long personId) {
        this.personId = personId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Tweet setContent(String content) {
        this.content = content;
        return this;
    }
}
