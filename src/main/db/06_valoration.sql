CREATE TABLE challenge_accepted.valoration (
  id SERIAL PRIMARY KEY,
  id_challenge INT NOT NULL REFERENCES challenge_accepted.challenge(id), 
  id_user INT NOT NULL REFERENCES challenge_accepted.user(id),
  id_valorator INT NOT NULL REFERENCES challenge_accepted.user(id),
  valoration INT
);