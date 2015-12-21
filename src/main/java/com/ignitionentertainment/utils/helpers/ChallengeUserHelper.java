/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.ChallengeUser;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class ChallengeUserHelper {
    Session session = null;
    
    public ChallengeUserHelper(){
    }
    
    public void createChallengeUser(ChallengeUser challengeUser) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(challengeUser);
        session.getTransaction().commit();
        session.close();
    }
    
    public int addUserToChallenge(int user_id, int challenge_id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.challenge_user (id_challenge, id_user) VALUES (" +
        								challenge_id +  ", " + user_id +
        								") RETURNING id");
        int id = (int)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return id;
    }
    
    public ChallengeUser getChallengeUserById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from ChallengeUser where id = " + id);
        ChallengeUser challengeUser = (ChallengeUser)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return challengeUser;
    }
    
    public ChallengeUser getChallengeUserByChallengeAndUserIds(int challenge_id, int user_id) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from ChallengeUser where id_challenge = " + challenge_id + " and id_user = " + user_id);
        ChallengeUser challengeUser = (ChallengeUser)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return challengeUser;
    }
    
    public void updateChallengeUser(ChallengeUser challengeUser) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(challengeUser);
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteChallengeUser(ChallengeUser challengeUser) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // TODO: Check if user is the creator of the challenge, and also, check if the user is in the challenge
        // Get challenge
        // Compare ids
        // Delete other related entities
        
        // TODO: Check dates and decide if the row can be deleted or not 
        session.delete(challengeUser);
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteUserFromChallenge(User user, Challenge challenge) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query getChallengeUser = session.createQuery("from ChallengeUser where ChallengeUser.id = " + challenge.getId() + " and ChallengeUser.id = " + user.getId());
        ChallengeUser challengeUser = (ChallengeUser)getChallengeUser.uniqueResult();
        session.getTransaction().commit();
        session.close();
        deleteChallengeUser(challengeUser);
    }
    
    public List<User> getUsersFromChallengeId(int challengeId) {
    	List<User> users = new ArrayList<>();
    	
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query getChallengeUser = session.createQuery("from ChallengeUser where id_challenge = " + challengeId);
        List<ChallengeUser> challengeUsers = (List<ChallengeUser>)getChallengeUser.list();
        session.getTransaction().commit();
        session.close();
        
        UserHelper userHelper = new UserHelper();
        
        for(ChallengeUser challengeUser : challengeUsers) {
        	users.add(userHelper.getUserById(challengeUser.getIdUser()));
        }
        
        return users;
    }
    
    public List<Challenge> getChallengesByUserId(int userId) {
    	List<Challenge> challenges = new ArrayList<>();
    	
    	session = HibernateUtil.getSessionFactory().openSession();
    	session.beginTransaction();
    	
    	Query getChallenges = session.createQuery("from ChallengeUser where id_user = " + userId);
    	List<ChallengeUser> challengeUsers = (List<ChallengeUser>)getChallenges.list();
    	
    	session.getTransaction().commit();
    	session.close();
    	
    	ChallengeHelper challengeHelper = new ChallengeHelper();
    	
    	for(ChallengeUser challengeUser : challengeUsers) {
    		challenges.add(challengeHelper.getChallengeById(challengeUser.getIdChallenge()));
        }
    	
    	return challenges;
    }
}
