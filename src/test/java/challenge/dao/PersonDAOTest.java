package challenge.dao;

import challenge.dao.api.PersonDAO;
import challenge.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by sasiddi on 4/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDAOTest {

    @Autowired
    PersonDAO personDAO;

    private final Long personId = 1l; //expecting this id to be in the database!!
    private final String username = "rigel"; //expecting this user to be in the database!!

    @Test
    public void getByIdTest() {
        Person person = personDAO.getById(personId);
        assertNotNull(person);
        assertEquals(personId, person.getId());
    }

    @Test
    public void getByUsernameTest() {
        Person person = personDAO.getByUsername(username);
        assertNotNull(person);
        assertEquals(username, person.getUsername());
    }

    @Test
    public void getNull() {
        Person person = personDAO.getByUsername(UUID.randomUUID().toString());
        assertNull(person);
    }
}
