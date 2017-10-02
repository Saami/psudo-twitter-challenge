package challenge.dao.impl;

import challenge.dao.api.PersonDAO;
import challenge.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sasiddi on 4/16/17.
 */
@Repository
public class PersonDAOImpl implements PersonDAO{
    private static final Logger LOG = LoggerFactory.getLogger(PersonDAOImpl.class);

    static final String TABLE_NAME = "PERSON";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private static final String getByUsernameQuery = String.format("SELECT * FROM %s WHERE USERNAME=:username", TABLE_NAME);
    @Override
    public Person getByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("non empty username is required to retrieve Person");
        }
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("username", username);
            Map<String, Object> personMap = namedParameterJdbcTemplate.queryForMap(getByUsernameQuery, paramMap);
            return getPersonObject(personMap);
        } catch (Exception e) {
            LOG.error(String.format("error retrieving person. username: %s", username), e);
            return null;
        }
    }

    private static final String getByIdQuery = String.format("SELECT * FROM %s WHERE ID = :personId", TABLE_NAME);
    @Override
    public Person getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is required to retrieve Person");
        }
        try {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("personId", id);
        Map<String, Object> personMap = namedParameterJdbcTemplate.queryForMap(getByIdQuery, paramMap);
        return getPersonObject(personMap);
        } catch (Exception e) {
            LOG.error(String.format("error retrieving person. id: %s", id), e);
            return null;
        }
    }

    private Person getPersonObject(Map<String, Object> result) {
        Person person = null;
        try {
            person = new Person()
            .setId((Long) result.get("ID"))
                    .setName((String) result.get("NAME"))
                    .setUsername((String) result.get("USERNAME"))
                    .setPassword((String) result.get("PASSWORD"));
        } catch (Exception e) {
            LOG.error("Error getting person object from resultSet");
        }
        return person;
    }
}
