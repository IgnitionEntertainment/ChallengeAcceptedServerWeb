package com.ignitionentertainment.ws;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ignitionentertainment.bo.ChallengeAcceptedBo;
import com.ignitionentertainment.bo.impl.ChallengeAcceptedBoImpl;
import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.dao.PendingChange;
import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.dao.Valoration;
import com.ignitionentertainment.dto.PendingChangesJsonBean;
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
import com.ignitionentertainment.exceptions.NonExistingChallengeException;
import com.ignitionentertainment.exceptions.NonExistingChallengeUserException;
import com.ignitionentertainment.exceptions.NonExistingUserException;

@RequestScoped
@Path("/webservice")
@WebService
public class ChallengeAcceptedWs {

	//TODO: Aixo s'hauria de fer amb injeccio de dependencies de spring
	ChallengeAcceptedBo challengeAcceptedBo = new ChallengeAcceptedBoImpl();
	
	@GET
	@Path("/pendingchanges/{iduser}")
	@Produces(MediaType.APPLICATION_JSON)
	public PendingChangesJsonBean[] getPendingChanges( @PathParam("iduser") int idUser ){
		
		List<PendingChange> pendingChanges = challengeAcceptedBo.getUserPendingChanges(idUser);
		
		PendingChangesJsonBean[] result = new PendingChangesJsonBean[pendingChanges.size()];
		
		int i = 0;
		for(PendingChange pendingChange : pendingChanges){
			PendingChangesJsonBean resultPendingChange = new PendingChangesJsonBean();
			resultPendingChange.setChange(pendingChange.getChange());
			resultPendingChange.setIdChallenge(pendingChange.getIdChallenge());
			resultPendingChange.setIdUser(pendingChange.getIdUser());
			result[i] = resultPendingChange;
			i=i+1;
		}
		
		return result;
	}

	@WebMethod
	public boolean signUp(User user,Location location) throws ExistingUserEmailException, ExistingUsernameException{
		return challengeAcceptedBo.signUp(user,location);
	}
	
	@WebMethod
	public User logIn(User user) throws InvalidLoginException{
		return challengeAcceptedBo.LogIn(user);
	}
	
	@WebMethod
	public UserInfoDto getUserInfoById(int userId) throws NonExistingUserException{
		return challengeAcceptedBo.getUserInfoById(userId);
	}
	
	@WebMethod
	public List<RankingDto> getRanking(){
		return challengeAcceptedBo.getRanking();
	}
	
	@WebMethod
	public Score getScoreByUserId(int userId) throws NonExistingUserException {
		return challengeAcceptedBo.getScoreByUserId(userId);
	}
	
	@WebMethod
	public boolean addScores(List<UserScore> userScores) throws NonExistingUserException {
		return challengeAcceptedBo.addScores(userScores);
	}
	
	public boolean createChallenge(Challenge c, Location l) throws ClosingTimeAfterChallengeTimeException, ClosingTimeBeforeCurrentTimeException{
		return challengeAcceptedBo.createChallenge(c, l);
	}
	
	@WebMethod
	public boolean updateChallenge(Challenge c){
		return challengeAcceptedBo.updateChallenge(c);
	}
	
	@WebMethod
	public Challenge getChallenge(int id) throws NonExistingChallengeException{
		return challengeAcceptedBo.getChallenge(id);
	}
	
	@WebMethod
	public boolean deleteChallenge(int id){
		return challengeAcceptedBo.deleteChallenge(id);
	}
	
	@WebMethod
	public boolean addTagsToChallenge(List<Tag> tags, int challengeId) throws NonExistingChallengeException {
		return challengeAcceptedBo.addTagsToChallenge(tags, challengeId);
	}
	
	@WebMethod
	public List<Tag> getTagsOfChallenge(int challengeId) throws NonExistingChallengeException {
		return challengeAcceptedBo.getTagsOfChallenge(challengeId);
	}
	
	@WebMethod
	public List<User> getUsersOfChallenge(int challengeId) throws NonExistingChallengeException {
		return challengeAcceptedBo.getUsersOfChallenge(challengeId);
	}
	
	@WebMethod
	public boolean addUserToChallenge(int userId, int challengeId) throws ExistingChallengeUserException, MaximumNumberOfParticipantsReachedException {
		return challengeAcceptedBo.addUserToChallenge(userId, challengeId);
	}
	
	@WebMethod
	public List<Challenge> getUserChallenges(int userId) throws NonExistingUserException {
		return challengeAcceptedBo.getUserChallenges(userId);
	}
	
	@WebMethod
	public boolean deleteUserFromChallenge(int userId, int challengeId) throws NonExistingChallengeUserException, ClosingTimeBeforeCurrentTimeException, DeleteChallengeFromUserCreatorException {
		return challengeAcceptedBo.deleteUserFromChallenge(userId, challengeId);
	}
	
	@WebMethod
	public boolean addValorations(List<Valoration> valorations) throws ExistingValorationException, InvalidValorationsException {
		return challengeAcceptedBo.addValorations(valorations);
	}
	
	@WebMethod
	public float getAverageValorationfromUser(int userId) throws NonExistingUserException {
		return challengeAcceptedBo.getAverageValorationfromUser(userId);
	}
	
	@WebMethod
	public boolean createLocation(Location location) {
		return challengeAcceptedBo.createLocation(location);
	}
	
	@WebMethod
	public boolean updateLocation(Location location) {
		return challengeAcceptedBo.updateLocation(location);
	}
	
	@WebMethod
	public Location getLocationById(int locationId) {
		return challengeAcceptedBo.getLocationById(locationId);
	}
	
	@WebMethod
	public List<Challenge> getNearChallenges(Location location) {
		return challengeAcceptedBo.getNearChallenges(location);
	}
	
	
}
