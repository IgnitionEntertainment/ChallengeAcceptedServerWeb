CREATE TABLE challenge_accepted.location (
  id            SERIAL PRIMARY KEY,
  geog GEOGRAPHY(Point, 4326)
);

CREATE INDEX location_gix
  ON challenge_accepted.location 
  USING GIST (geog);