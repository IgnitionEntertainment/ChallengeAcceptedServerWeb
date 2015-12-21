CREATE TABLE challenge_accepted.score(
	id SERIAL PRIMARY KEY,
	won_challenges INT,
	lost_challenges INT,
	total_challenges INT,
	total_score INT
);