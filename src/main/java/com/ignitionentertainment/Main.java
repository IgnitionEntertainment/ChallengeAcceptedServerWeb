/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment;

import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.dao.Valoration;
import com.ignitionentertainment.dto.RankingDto;
import com.ignitionentertainment.dto.UserInfoDto;
import com.ignitionentertainment.exceptions.ClosingTimeAfterChallengeTimeException;
import com.ignitionentertainment.exceptions.ClosingTimeBeforeCurrentTimeException;
import com.ignitionentertainment.exceptions.DeleteChallengeFromUserCreatorException;
import com.ignitionentertainment.exceptions.ExistingChallengeUserException;
import com.ignitionentertainment.exceptions.ExistingValorationException;
import com.ignitionentertainment.exceptions.InvalidValorationsException;
import com.ignitionentertainment.exceptions.MaximumNumberOfParticipantsReachedException;
import com.ignitionentertainment.exceptions.NonExistingChallengeException;
import com.ignitionentertainment.exceptions.NonExistingChallengeUserException;
import com.ignitionentertainment.exceptions.NonExistingUserException;
import com.ignitionentertainment.utils.helpers.TagHelper;
import com.ignitionentertainment.ws.ChallengeAcceptedWs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author JPPL
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*TagHelper tagHelper = new TagHelper();
        
        List<Tag> tagList = tagHelper.getAllTags();
        
        for(Tag tag : tagList){
            System.out.println(tag.getTag());
        }
        */
 
        
        ChallengeAcceptedWs facade = new ChallengeAcceptedWs();
        
        Challenge ch = new Challenge();
        Challenge ch2 = new Challenge();

        //Users
        Location location = new Location();
        location.setLatitude(10.9);
        location.setLongitude(8.545);
        User user = new User();
        user.setEmail("lopez@kremlin.com");
        user.setUsername("waikiki");
        user.setIdAvatar(99);
        user.setPassword("#######");
        
        Location location2 = new Location();
        location2.setLatitude(44.78);
        location2.setLongitude(85.98);
        User user2 = new User();
        user2.setEmail("user2@gmail.com");
        user2.setUsername("user2_name");
        user2.setIdAvatar(99);
        user2.setPassword("pass2");
        
        Location location10 = new Location();
        location10.setLatitude(44.78);
        location10.setLongitude(85.98);
        User user3 = new User();
        user3.setEmail("user3@gmail.com");
        user3.setUsername("user3_name");
        user3.setIdAvatar(99);
        user3.setPassword("pass3");
        
        Location location11 = new Location();
        location11.setLatitude(44.78);
        location11.setLongitude(85.98);
        User user4 = new User();
        user4.setEmail("user4@gmail.com");
        user4.setUsername("user4_name");
        user4.setIdAvatar(99);
        user4.setPassword("pass4");
        
        try{
        	facade.signUp(user, location);
        	facade.signUp(user2, location2);
        	facade.signUp(user3, location10);
        	facade.signUp(user4, location11);
        }catch(Exception e){
        	e.printStackTrace();
        }

        Location location3 = new Location();
        location3.setLatitude(10.900001);
        location3.setLongitude(8.545);
        
        
        Calendar closingCalendar = Calendar.getInstance();
        closingCalendar.add(Calendar.DAY_OF_MONTH, 1);
        Date closingTime = closingCalendar.getTime();
        Calendar challengeCalendar = Calendar.getInstance();
        challengeCalendar.add(Calendar.DAY_OF_MONTH, 1);
        challengeCalendar.add(Calendar.HOUR_OF_DAY, 3);
        Date challengeTime = challengeCalendar.getTime();
        
        ch.setChallengeTime(challengeTime);
        ch.setClosingTime(closingTime);
        ch.setComments("comment");
        ch.setCreationTime(new Date());
        ch.setIdCreator(user.getId());
        ch.setIdState(1);
        ch.setName("challenge name");
        ch.setNumParticipants(5);
        
        Location location4 = new Location();
        location4.setLatitude(74.78);
        location4.setLongitude(22.98);
        
        Calendar closingCalendar2 = Calendar.getInstance();
        closingCalendar2.add(Calendar.DAY_OF_MONTH, 1);
        Date closingTime2 = closingCalendar.getTime();
        Calendar challengeCalendar2 = Calendar.getInstance();
        challengeCalendar2.add(Calendar.DAY_OF_MONTH, 1);
        challengeCalendar2.add(Calendar.HOUR_OF_DAY, 3);
        Date challengeTime2 = challengeCalendar2.getTime();
        
        ch2.setChallengeTime(challengeTime2);
        ch2.setClosingTime(closingTime2);
        ch2.setComments("comment2");
        ch2.setCreationTime(new Date());
        ch2.setIdCreator(user2.getId());
        ch2.setIdState(1);
        ch2.setName("challenge name2");
        ch2.setNumParticipants(5);
        
        Tag tag = new Tag();
        tag.setTag("tag1");
        Tag tag2 = new Tag();
        tag2.setTag("tag2");
        Tag tag3 = new Tag();
        tag3.setTag("tag3");
        Tag tag4 = new Tag();
        tag4.setTag("tag4");
        Tag tag5 = new Tag();
        tag5.setTag("tag5");
        
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        
        try {
			facade.createChallenge(ch, location3);
			facade.addTagsToChallenge(tags, ch.getId());
			facade.addUserToChallenge(user2.getId(), ch.getId());
			facade.addUserToChallenge(user3.getId(), ch.getId());
			facade.addUserToChallenge(user4.getId(), ch.getId());
			
			facade.createChallenge(ch2, location4);
			
			List<Tag> obtainedTags = facade.getTagsOfChallenge(ch.getId());
			for(Tag currentTag : obtainedTags) {
				System.out.println(currentTag.getTag());
			}
			
			List<User> obtainedUsers = facade.getUsersOfChallenge(ch.getId());
			System.out.println("There are: " + obtainedUsers.size() + " users");
			for(User currentUser : obtainedUsers) {
				System.out.println(currentUser.getEmail());
			}
			
			List<Challenge> obtainedChallenges = facade.getUserChallenges(user2.getId());
			System.out.println("User " + user2.getUsername() + " has " + obtainedChallenges.size());
			for(Challenge currentChallenge : obtainedChallenges) {
				System.out.println(currentChallenge.getName());
			}
			
			facade.deleteUserFromChallenge(user2.getId(), ch.getId());
			
			List<User> obtainedUsers2 = facade.getUsersOfChallenge(ch.getId());
			System.out.println("There are: " + obtainedUsers2.size() + " users");
			for(User currentUser : obtainedUsers2) {
				System.out.println(currentUser.getEmail());
			}
			
			facade.addUserToChallenge(user2.getId(), ch.getId());
			
			List<Valoration> valorations = new ArrayList<>();
			
			Valoration valoration = new Valoration.ValorationBuilder()
					.setIdValorator(user.getId())
					.setIdUser(user2.getId())
					.setIdChallenge(ch.getId())
					.setValoration(1)
					.Build();
			valorations.add(valoration);
			
			Valoration valoration2 = new Valoration.ValorationBuilder()
					.setIdValorator(user.getId())
					.setIdUser(user3.getId())
					.setIdChallenge(ch.getId())
					.setValoration(1)
					.Build();
			valorations.add(valoration2);
			
			Valoration valoration3 = new Valoration.ValorationBuilder()
					.setIdValorator(user.getId())
					.setIdUser(user4.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations.add(valoration3);
			
			facade.addValorations(valorations);
			
			List<Valoration> valorations2 = new ArrayList<>();
			
			Valoration valoration2_1 = new Valoration.ValorationBuilder()
					.setIdValorator(user2.getId())
					.setIdUser(user.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations2.add(valoration2_1);
			
			Valoration valoration2_2 = new Valoration.ValorationBuilder()
					.setIdValorator(user2.getId())
					.setIdUser(user3.getId())
					.setIdChallenge(ch.getId())
					.setValoration(1)
					.Build();
			valorations2.add(valoration2_2);
			
			Valoration valoration2_3 = new Valoration.ValorationBuilder()
					.setIdValorator(user2.getId())
					.setIdUser(user4.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations2.add(valoration2_3);
			
			facade.addValorations(valorations2);
			
			List<Valoration> valorations3 = new ArrayList<>();
			
			Valoration valoration3_1 = new Valoration.ValorationBuilder()
					.setIdValorator(user3.getId())
					.setIdUser(user.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations3.add(valoration3_1);
			
			Valoration valoration3_2 = new Valoration.ValorationBuilder()
					.setIdValorator(user3.getId())
					.setIdUser(user2.getId())
					.setIdChallenge(ch.getId())
					.setValoration(1)
					.Build();
			valorations3.add(valoration3_2);
			
			Valoration valoration3_3 = new Valoration.ValorationBuilder()
					.setIdValorator(user3.getId())
					.setIdUser(user4.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations3.add(valoration3_3);
			
			facade.addValorations(valorations3);
			
			List<Valoration> valorations4 = new ArrayList<>();
			
			Valoration valoration4_1 = new Valoration.ValorationBuilder()
					.setIdValorator(user4.getId())
					.setIdUser(user.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations4.add(valoration4_1);
			
			Valoration valoration4_2 = new Valoration.ValorationBuilder()
					.setIdValorator(user4.getId())
					.setIdUser(user2.getId())
					.setIdChallenge(ch.getId())
					.setValoration(0)
					.Build();
			valorations4.add(valoration4_2);
			
			Valoration valoration4_3 = new Valoration.ValorationBuilder()
					.setIdValorator(user4.getId())
					.setIdUser(user3.getId())
					.setIdChallenge(ch.getId())
					.setValoration(1)
					.Build();
			valorations4.add(valoration4_3);
			
			facade.addValorations(valorations4);
			
			float averageUser2 = facade.getAverageValorationfromUser(user2.getId());
			System.out.println("Average: " + averageUser2);
			
			
			List<Challenge> nearChallenges = facade.getNearChallenges(location);
			System.out.println("Near challenges");
			for(Challenge challenge : nearChallenges) {
				System.out.println("Challenge : " + challenge.getId() + " " + challenge.getName());
			}
			
		} catch (ClosingTimeAfterChallengeTimeException | 
				ClosingTimeBeforeCurrentTimeException | 
				NonExistingChallengeException | 
				ExistingChallengeUserException | 
				MaximumNumberOfParticipantsReachedException | 
				NonExistingUserException | 
				NonExistingChallengeUserException | 
				DeleteChallengeFromUserCreatorException | 
				ExistingValorationException | 
				InvalidValorationsException e) {
			e.printStackTrace();
		}
        
        /*
        User user = new User();
        user.setUsername("waikiki");
        user.setPassword("#######");
        try{
        	User loggedUser = facade.logIn(user);
        	System.out.println(loggedUser);
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        
        List<RankingDto> ranking = facade.getRanking();
        System.out.println("##########");
        System.out.println("##########");
        
        
        UserInfoDto userInfo;
        try {
			userInfo = facade.getUserInfoById(5);
	        System.out.println("##########");
	        System.out.println("##########");
		} catch (NonExistingUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
    }
    
}
