package com.ignitionentertainment.dto;

import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.dao.User;

public class UserInfoDto {
	private User user;
	private Location location;
	private Score score;
	
	public UserInfoDto(){
		
	}
	
	public UserInfoDto(User user, Location location, Score score) {
		this.user = user;
		this.location = location;
		this.score = score;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	
	
}
