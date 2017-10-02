package challenge.service.impl;

import challenge.dao.api.PersonDAO;
import challenge.model.Person;
import challenge.service.api.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by sasiddi on 4/16/17.
 */
@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    PersonDAO personDAO;

    @Override
    public Person getByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("username must be provided");
        }

        return personDAO.getByUsername(username);
    }

    @Override
    public Person getPersonById(Long personId) {
        if (personId == null) {
            throw new IllegalArgumentException("personId must be provided");
        }

        return personDAO.getById(personId);
    }
}
