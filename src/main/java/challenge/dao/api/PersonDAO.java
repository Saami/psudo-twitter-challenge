package challenge.dao.api;

import challenge.model.Person;

import java.util.List;


/**
 * Created by sasiddi on 4/14/17.
 */
public interface PersonDAO {
    Person getByUsername(String username);
    Person getById(Long id);

}
