/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class ChallengeHelper {
    Session session = null;
    
    public ChallengeHelper(){
    }
    
    public Challenge getChallengeById(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Challenge where id = "+String.valueOf(id));
        Challenge result = (Challenge)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public Challenge getChallengeByLocalizationId(int localizationId){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Challenge where idLocation = " + localizationId);
        Challenge result = (Challenge)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public void createChallenge(Challenge c){
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        c.setModificationTime(c.getCreationTime());
        session.save(c);
        session.getTransaction().commit();
        session.close();
    }
    
    public void updateChallenge(Challenge c){
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(c);
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteChallenge(int id){
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(getChallengeById(id));
        session.getTransaction().commit();
        session.close();
    }
}
