CREATE TABLE challenge_accepted.challenge_user (
  id            SERIAL PRIMARY KEY,
  id_challenge	INT NOT NULL REFERENCES challenge_accepted.challenge(id),
  id_user		INT NOT NULL REFERENCES challenge_accepted.user(id)
);