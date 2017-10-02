package challenge.service.api;

import challenge.model.Person;



/**
 * Created by sasiddi on 4/16/17.
 */
public interface PersonService {
    Person getByUsername(String username);
    Person getPersonById(Long personId);

}
