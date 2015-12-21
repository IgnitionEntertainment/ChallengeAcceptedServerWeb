/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.ChallengeTag;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class ChallengeTagHelper {
    Session session = null;
    
    public ChallengeTagHelper(){
    }
    
    public ChallengeTag getChallengeTagById(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from ChallengeTag where id = "+String.valueOf(id));
        ChallengeTag result = (ChallengeTag)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public int createChallengeTag(ChallengeTag challengeTag) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.challenge_tag(id_challenge, id_tag) VALUES (" +
				challengeTag.getIdChallenge() +  ", " + challengeTag.getIdTag() +
				") RETURNING id");
        int id = (int)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return id;
    }
    
    public boolean deleteChallengeTag(ChallengeTag challengeTag) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(challengeTag);
        session.getTransaction().commit();
        session.close();
        return true;
    }
    
    public List<ChallengeTag> getChallengeTagsByChallengeId(int idChallenge) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from ChallengeTag where id_challenge = " + idChallenge);
        List<ChallengeTag> result = (List<ChallengeTag>)q.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public List<ChallengeTag> getChallengeTagsByTagId(int idTag) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from ChallengeTag where id_tag = " + idTag);
        List<ChallengeTag> result = (List<ChallengeTag>)q.list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
