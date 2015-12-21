CREATE TABLE challenge_accepted.challenge (
  id               SERIAL PRIMARY KEY,
  creation_time    TIMESTAMP,
  name             TEXT,
  num_participants INT,
  id_state         INT,
  id_location      INT REFERENCES challenge_accepted.location(id),
  closing_time     TIMESTAMP,
  challenge_time   TIMESTAMP,
  comments         TEXT,
  id_creator       INT REFERENCES challenge_accepted.user(id),
  modification_time TIMESTAMP
);
