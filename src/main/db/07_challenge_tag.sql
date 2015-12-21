CREATE TABLE challenge_accepted.challenge_tag (
  id            SERIAL PRIMARY KEY,
  id_challenge	INT NOT NULL REFERENCES challenge_accepted.challenge(id),
  id_tag		INT NOT NULL REFERENCES challenge_accepted.tag(id)
);