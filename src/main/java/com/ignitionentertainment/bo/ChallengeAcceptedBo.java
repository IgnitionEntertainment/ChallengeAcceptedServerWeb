package com.ignitionentertainment.bo;

import java.util.List;

import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.dao.PendingChange;
import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.dao.User;
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
import com.ignitionentertainment.dao.Valoration;
import com.ignitionentertainment.dto.RankingDto;
import com.ignitionentertainment.dto.UserInfoDto;
import com.ignitionentertainment.dto.UserScore;

public interface ChallengeAcceptedBo {

	boolean signUp(User user, Location location) throws ExistingUserEmailException, ExistingUsernameException;
	
	//Retorna el usuari loguejat en cas de que tot funcioni correctament.
	User LogIn(User user) throws InvalidLoginException;
	
	UserInfoDto getUserInfoById(int userId) throws NonExistingUserException;
	
	List<RankingDto> getRanking();
	
	//------------------------------
	boolean createChallenge(Challenge challenge, Location location) throws ClosingTimeAfterChallengeTimeException, ClosingTimeBeforeCurrentTimeException;
	
	Challenge getChallenge(int id) throws NonExistingChallengeException;
	
	boolean updateChallenge(Challenge challenge);
	
	boolean deleteChallenge(int id);
	//--------------------------------
	
	boolean addUserToChallenge(int userId, int challengeId) throws ExistingChallengeUserException, MaximumNumberOfParticipantsReachedException;
	
	boolean deleteUserFromChallenge(int userId, int challengeId) throws NonExistingChallengeUserException, ClosingTimeBeforeCurrentTimeException, DeleteChallengeFromUserCreatorException;
	
	boolean addValorations(List<Valoration> valorations) throws ExistingValorationException, InvalidValorationsException;
	
	float getAverageValorationfromUser(int userId) throws NonExistingUserException;
	
	boolean addScores(List<UserScore> userScores) throws NonExistingUserException;
	
	Score getScoreByUserId(int userId) throws NonExistingUserException;
	
	List<Challenge> getNearChallenges(Location location);
	
	List<Challenge> getUserChallenges(int userId) throws NonExistingUserException;
	
	List<User> getUsersOfChallenge(int challengeId) throws NonExistingChallengeException;
	
	boolean addTagsToChallenge(List<Tag> tags, int challengeId) throws NonExistingChallengeException;
	
	List<Tag> getTagsOfChallenge(int challengeId) throws NonExistingChallengeException;
	
	boolean createLocation(Location location);
	
	Location getLocationById(int locationId);
	
	boolean updateLocation(Location location);
	
	List<PendingChange> getUserPendingChanges(int idUser);

}
