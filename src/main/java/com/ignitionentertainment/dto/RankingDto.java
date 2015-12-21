package com.ignitionentertainment.dto;

import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.dao.User;

public class RankingDto {

	private User user;
	private Score score;
	
	public RankingDto(){
		
	}
	
	public RankingDto(User user, Score score) {
		this.user = user;
		this.score = score;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
}
