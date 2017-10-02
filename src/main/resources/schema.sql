DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS tweet;
DROP TABLE IF EXISTS followers;

CREATE TABLE person (id IDENTITY, name VARCHAR, username VARCHAR, password CHAR(64) DEFAULT 'password', UNIQUE KEY(username));
CREATE TABLE tweet (id IDENTITY, person_id NUMBER REFERENCES person(id), content VARCHAR);
CREATE TABLE followers (id INT AUTO_INCREMENT, person_id NUMBER REFERENCES person (id), follower_person_id NUMBER REFERENCES person (id), PRIMARY KEY (person_id, follower_person_id));