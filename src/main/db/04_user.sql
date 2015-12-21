CREATE TABLE challenge_accepted.user (
  id SERIAL PRIMARY KEY NOT NULL,
  username TEXT,
  email TEXT,
  password TEXT,
  id_avatar INT,
  id_score INT NOT NULL REFERENCES challenge_accepted.score(id),
  id_location INT REFERENCES challenge_accepted.location(id)
);