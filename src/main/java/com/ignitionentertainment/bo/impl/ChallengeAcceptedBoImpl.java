package com.ignitionentertainment.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ignitionentertainment.bo.ChallengeAcceptedBo;
import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.ChallengeTag;
import com.ignitionentertainment.dao.ChallengeUser;
import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.dao.PendingChange;
import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.dao.Valoration;
import com.ignitionentertainment.dto.RankingDto;
import com.ignitionentertainment.dto.UserInfoDto;
import com.ignitionentertainment.dto.UserScore;
import com.ignitionentertainment.exceptions.ClosingTimeAfterChallengeTimeException;
import com.ignitionentertainment.exceptions.ClosingTimeBeforeCurrentTimeException;
import com.ignitionentertainment.exceptions.DeleteChallengeFromUserCreatorException;
import com.ignitionentertainment.exceptions.ExistingChallengeUserException;
import com.ignitionentertainment.exceptions.ExistingUserEmailException;
import com.ignitionentertainment.exceptions.ExistingUsernameException;
import com.ignitionentertainment.exceptions.ExistingValorationException;
import com.ignitionentertainment.exceptions.InvalidLoginException;
import com.ignitionentertainment.exceptions.InvalidValorationsException;
import com.ignitionentertainment.exceptions.MaximumNumberOfParticipantsReachedException;
import com.ignitionentertainment.exceptions.NonExistingUserException;
import com.ignitionentertainment.exceptions.NonExistingChallengeException;
import com.ignitionentertainment.exceptions.NonExistingChallengeUserException;
import com.ignitionentertainment.utils.helpers.ChallengeTagHelper;
import com.ignitionentertainment.utils.helpers.ChallengeHelper;
import com.ignitionentertainment.utils.helpers.ChallengeUserHelper;
import com.ignitionentertainment.utils.helpers.LocationHelper;
import com.ignitionentertainment.utils.helpers.PendingChangeHelper;
import com.ignitionentertainment.utils.helpers.ScoreHelper;
import com.ignitionentertainment.utils.helpers.TagHelper;
import com.ignitionentertainment.utils.helpers.UserHelper;
import com.ignitionentertainment.utils.helpers.ValorationHelper;


public class ChallengeAcceptedBoImpl implements ChallengeAcceptedBo{

	public static final int WIN_SCORE_DEFAULT = 10;
	public static final int LOSE_SCORE_DEFAULT = 5;
	public static final int CHALLENGE_FINISHED = 0;
	public static final int CHALLENGE_ACTIVE = 1;
	public static final int RADIUS = 1000;
	
	public ChallengeAcceptedBoImpl(){
		
	}

	@Override
	public boolean signUp(User user, Location location) throws ExistingUserEmailException, ExistingUsernameException {
		
		checkTakenUserFields(user);
		checkLocation(user, location);
		createScore(user);
		
		UserHelper userHelper = new UserHelper();
		userHelper.createUser(user);

		return true;
	}
	
	private void checkTakenUserFields(User user) throws ExistingUserEmailException, ExistingUsernameException{
		UserHelper userHelper = new UserHelper();
		User searchedUser = userHelper.findUserByEmail(user.getEmail());
		if(searchedUser!=null){
			throw new ExistingUserEmailException();
		}
		searchedUser = userHelper.findUserByUsername(user.getUsername());
		if(searchedUser!=null){
			throw new ExistingUsernameException();
		}
	}
	
	private void checkLocation(User user, Location location){
		if(location != null){
			LocationHelper locationHelper = new LocationHelper();
			int locationId = locationHelper.createLocation(location);
			user.setIdLocation(locationId);
		}
	}
	
	private void createScore(User user){
		ScoreHelper scoreHelper = new ScoreHelper();
		int scoreId = scoreHelper.createScore();
		user.setIdScore(scoreId);
	}

	@Override
	public User LogIn(User user) throws InvalidLoginException {
		checkUserExist(user);
		UserHelper userHelper = new UserHelper(); 
		User loggedUser = userHelper.getUserByUsernameAndPassword(user.getUsername(),user.getPassword());
		if(loggedUser==null){
			throw new InvalidLoginException();
		}
		return loggedUser;
	}
	
	private void checkUserExist(User user) throws InvalidLoginException{
		UserHelper userHelper = new UserHelper();
		User searchedUser = userHelper.findUserByUsername(user.getUsername());
		if(searchedUser == null){
			throw new InvalidLoginException();
		}		
	}
	
	@Override
	public UserInfoDto getUserInfoById(int userId) throws NonExistingUserException {
		UserInfoDto result = new UserInfoDto();
		
		UserHelper userHelper = new UserHelper();
		User user = userHelper.getUserById(userId);
		if(user==null){
			throw new NonExistingUserException();
		}
		result.setUser(user);
		
		LocationHelper locationHelper = new LocationHelper();
		Location location = locationHelper.getLocationById(user.getIdLocation());
		if(location!=null){
			result.setLocation(location);
		}
		
		ScoreHelper scoreHelper = new ScoreHelper();
		Score score = scoreHelper.getScoreById(user.getIdScore());
		result.setScore(score);
				
		return result;
	}

	@Override
	public boolean createChallenge(Challenge challenge, Location location) throws ClosingTimeAfterChallengeTimeException, ClosingTimeBeforeCurrentTimeException {
		
		Date currentDate = new Date();
		
		if(challenge.getClosingTime().after(challenge.getChallengeTime())) {
			throw new ClosingTimeAfterChallengeTimeException();
		}
		
		if(challenge.getClosingTime().before(currentDate)) {
			throw new ClosingTimeBeforeCurrentTimeException();
		}
		
		try{
			ChallengeHelper challenge_helper = new ChallengeHelper();
			LocationHelper locationHelper = new LocationHelper();
			int locationId = locationHelper.createLocation(location);
			challenge.setIdLocation(locationId);
			challenge_helper.createChallenge(challenge);
	        ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
	        challengeUserHelper.addUserToChallenge(challenge.getIdCreator(), challenge.getId());
			return true;
		}
		catch(Exception e){
			return false;
		}		
	}

	@Override
	public boolean updateChallenge(Challenge challenge) {
		try{
			ChallengeHelper challenge_helper = new ChallengeHelper();
			challenge_helper.updateChallenge(challenge);
			return true;
		}
		catch(Exception e){
			return false;
		}		
	}

	@Override
	public boolean deleteChallenge(int id) {
		try{
			ChallengeHelper challenge_helper = new ChallengeHelper();
			challenge_helper.deleteChallenge(id);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	@Override
	public Challenge getChallenge(int challengeId) throws NonExistingChallengeException {
		ChallengeHelper challengeHelper = new ChallengeHelper();
		Challenge challenge = challengeHelper.getChallengeById(challengeId);
		if(challenge != null){
			return challenge;
		}	
		else{
			throw new NonExistingChallengeException();
		}
	}

	@Override
	public boolean addUserToChallenge(int userId, int challengeId) throws ExistingChallengeUserException, MaximumNumberOfParticipantsReachedException {
		ChallengeHelper challengeHelper = new ChallengeHelper();
		ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
		
		ChallengeUser checkChallengeUser = challengeUserHelper.getChallengeUserByChallengeAndUserIds(userId, challengeId);
		
		if(checkChallengeUser != null) {
			throw new ExistingChallengeUserException();
		}
		
		Challenge challenge = challengeHelper.getChallengeById(challengeId);
		List<User> usersInTheChallenge = challengeUserHelper.getUsersFromChallengeId(challengeId);
		
		if(usersInTheChallenge.size() < challenge.getNumParticipants()) {
			challengeUserHelper.addUserToChallenge(userId, challengeId);
		} else {
			throw new MaximumNumberOfParticipantsReachedException();
		}
		
		return true;
	}

	@Override
	public boolean deleteUserFromChallenge(int userId, int challengeId) throws NonExistingChallengeUserException, ClosingTimeBeforeCurrentTimeException, DeleteChallengeFromUserCreatorException {
		ChallengeHelper challengeHelper = new ChallengeHelper();
		Challenge challenge = challengeHelper.getChallengeById(challengeId);
		Date currentDate = new Date();
		
		if(challenge.getClosingTime().before(currentDate)) {
			throw new ClosingTimeBeforeCurrentTimeException();
		}
		
		if(challenge.getIdCreator().equals(userId)) {
			throw new DeleteChallengeFromUserCreatorException();
		}
		
		ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
		ChallengeUser challengeUser = challengeUserHelper.getChallengeUserByChallengeAndUserIds(challengeId, userId);
		
		if(challengeUser == null) {
			throw new NonExistingChallengeUserException();
		}
		challengeUserHelper.deleteChallengeUser(challengeUser);
		return true;
	}

	private boolean checkValorations(List<Valoration> valorations) {
		if(valorations.size() > 0) {
			ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
			List<User> usersOfChallenge = challengeUserHelper.getUsersFromChallengeId(valorations.get(0).getIdChallenge());
			int numberOfUsers = 0;
			for(User currentUser : usersOfChallenge) {
				for(Valoration currentValoration : valorations) {
					if(currentValoration.getIdUser() == currentValoration.getIdValorator()) {
						return false;
					}
					
					if(currentUser.getId() == currentValoration.getIdUser()) {
						numberOfUsers++;
						break;
					}
				}
			}
			
			// size()-1 because a user can't make a valoration to himself
			if(numberOfUsers != usersOfChallenge.size()-1) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean addValorations(List<Valoration> valorations) throws ExistingValorationException, InvalidValorationsException {
		ValorationHelper valorationHelper = new ValorationHelper();
		ChallengeHelper challengeHelper = new ChallengeHelper();
		Date currentDate = new Date();
		
		if(valorations.size() == 0 ) {
			return false;
		}
		
		Challenge challenge = challengeHelper.getChallengeById(valorations.get(0).getIdChallenge());
		
		/*
		 * TODO: Commented to test the Main file without this restriction
		if(challenge.getChallengeTime().before(currentDate)) {
			throw new InvalidValorationsException();
		}*/
		
		if(!checkValorations(valorations)) {
			throw new InvalidValorationsException();
		}
		
		if(valorationHelper.valorationsExist(valorations)) {
			throw new ExistingValorationException();
		}
		
		for(Valoration valoration : valorations) {
			valorationHelper.createValoration(valoration);
		}
		
		return true;
	}

	@Override
	public List<Challenge> getNearChallenges(Location location) {
		LocationHelper locationHelper = new LocationHelper();
		
		// TODO: Instead of return all the challenges near a radius, for a next iteration
		// could be better obtain only active challenges (that is, not closed, not finished, not cancelled)
		List<Integer> challengeIds = locationHelper.getChallengeIdsNearToLocation(location, RADIUS);
		List<Challenge> challenges = new ArrayList<>();
		
		ChallengeHelper challengeHelper = new ChallengeHelper();
		for(Integer id : challengeIds) {
			challenges.add(challengeHelper.getChallengeById(id));
		}
		return challenges;
	}
	
	@Override
	public float getAverageValorationfromUser(int userId) throws NonExistingUserException {
		UserHelper userHelper = new UserHelper();
		
		User user = userHelper.getUserById(userId);
		if(user == null) {
			throw new NonExistingUserException();
		}
		
		ValorationHelper valorationHelper = new ValorationHelper();
		return valorationHelper.getAverageFromUser(userId);
	}

	@Override
	public List<Challenge> getUserChallenges(int userId) throws NonExistingUserException {
		ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
		UserHelper userHelper = new UserHelper();
		
		User user = userHelper.getUserById(userId);
		if(user == null) {
			throw new NonExistingUserException();
		}
		
		return challengeUserHelper.getChallengesByUserId(userId);
	}
	
	@Override
	public List<User> getUsersOfChallenge(int challengeId) throws NonExistingChallengeException {
		ChallengeUserHelper challengeUserHelper = new ChallengeUserHelper();
		ChallengeHelper challengeHelper = new ChallengeHelper();
		
		Challenge challenge = challengeHelper.getChallengeById(challengeId);
		if(challenge == null) {
			throw new NonExistingChallengeException();
		}
		
		return challengeUserHelper.getUsersFromChallengeId(challengeId);
	}

	@Override
	public boolean addScores(List<UserScore> userScores) throws NonExistingUserException {
		ScoreHelper scoreHelper = new ScoreHelper();
		for(UserScore userScore : userScores) {
			Score score = getScoreByUserId(userScore.getUserId());
			if (userScore.isWinner()) {
				score.addWonChallenge();
				score.addTotalScore(WIN_SCORE_DEFAULT);
			} else {
				score.addLostChallenge();
				score.addTotalScore(LOSE_SCORE_DEFAULT);
			}
			score.addTotalChallenge();
			scoreHelper.updateScore(score);
		}
		
		return true;
	}

	@Override
	public Score getScoreByUserId(int userId) throws NonExistingUserException {
		UserHelper userHelper = new UserHelper();
		User user = userHelper.getUserById(userId);
		
		if(user == null) {
			throw new NonExistingUserException();
		}
		
		ScoreHelper scoreHelper = new ScoreHelper();
		Score score = scoreHelper.getScoreById(user.getIdScore());
		
		return score;
	}

	@Override
	public List<RankingDto> getRanking() {
		List<RankingDto> resultList = new ArrayList<>();
		
		List<User> orderedUsers;
		UserHelper userHelper = new UserHelper();
		orderedUsers = userHelper.getUsersOrderedByScore();
		
		ScoreHelper scoreHelper = new ScoreHelper();
		for(User u : orderedUsers){
			Score s = scoreHelper.getScoreById(u.getIdScore());
			RankingDto r = new RankingDto(u, s);
			resultList.add(r);
		}
		
		return resultList;
	}

	public boolean addTagsToChallenge(List<Tag> tags, int challengeId) throws NonExistingChallengeException {
		TagHelper tagHelper = new TagHelper();
		Challenge challenge = getChallenge(challengeId);
		ChallengeTagHelper challengeTagHelper = new ChallengeTagHelper();
		
		Tag checkTag;
		for(Tag tag : tags) {
			if(!tag.getTag().trim().isEmpty()) {
				checkTag = tagHelper.getTagByName(tag.getTag());
				ChallengeTag challengeTag = new ChallengeTag();
				challengeTag.setIdChallenge(challenge.getId());
				int tagId;
				if(checkTag == null) {
					tagId = tagHelper.createTag(tag);
				} else {
					tagId = checkTag.getId();
				}
				challengeTag.setIdTag(tagId);
				challengeTagHelper.createChallengeTag(challengeTag);
			}
		}
		return true;
	}
	
	public List<Tag> getTagsOfChallenge(int idChallenge) throws NonExistingChallengeException {
		List<Tag> tags = new ArrayList<>();
		ChallengeTagHelper challengeTagHelper = new ChallengeTagHelper();
		TagHelper tagHelper = new TagHelper();
		ChallengeHelper challengeHelper = new ChallengeHelper();
		
		if(challengeHelper.getChallengeById(idChallenge) == null) {
			throw new NonExistingChallengeException();
		}
		
		List<ChallengeTag> challengeTags = challengeTagHelper.getChallengeTagsByChallengeId(idChallenge);
		for(ChallengeTag challengeTag : challengeTags) {
			tags.add(tagHelper.getTagById(challengeTag.getIdTag()));
		}
		return tags;
	}
	
	public boolean updateLocation(Location location) {
		LocationHelper locationHelper = new LocationHelper();
		locationHelper.updateLocation(location);
		return true;
	}
	
	public boolean createLocation(Location location) {
		LocationHelper locationHelper = new LocationHelper();
		locationHelper.createLocation(location);
		return true;
	}
	
	public Location getLocationById(int locationId) {
		LocationHelper locationHelper = new LocationHelper();
		return locationHelper.getLocationById(locationId);
	}

	@Override
	public List<PendingChange> getUserPendingChanges(int idUser) {
		PendingChangeHelper pendingChangeHelper = new PendingChangeHelper();
		return pendingChangeHelper.getChangesByUserId(idUser);
	}
	
}
