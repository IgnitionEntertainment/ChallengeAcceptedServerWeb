package com.ignitionentertainment.dto;

public class UserScore {
	
	private int user_id;
	private boolean is_winner;
	
	public UserScore(int user_id, boolean is_winner) {
		this.user_id = user_id;
		this.is_winner = is_winner;
	}
	
	public int getUserId() {
		return this.user_id;
	}
	
	public boolean isWinner() {
		return this.is_winner;
	}
}
