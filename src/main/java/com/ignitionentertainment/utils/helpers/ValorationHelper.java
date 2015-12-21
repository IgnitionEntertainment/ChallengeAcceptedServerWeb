/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Valoration;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class ValorationHelper {
    Session session = null;
    
    public ValorationHelper(){
    }
    
    public int createValoration(Valoration valoration) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.valoration (id_challenge, id_user, id_valorator, valoration) VALUES (" + 
        		  valoration.getIdChallenge() + ", " + valoration.getIdUser() + ", " +
        		  valoration.getIdValorator() + ", " + valoration.getValoration() + ") RETURNING id");
        int valorationId = (int) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return valorationId;
    }
    
    public Valoration getValorationById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Valoration where id = " + id);
        Valoration challengeUser = (Valoration)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return challengeUser;
    }
    
    public void updateValoration(Valoration valoration) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(valoration);
        session.getTransaction().commit();
        session.close();
    }
    
    public void deleteValoration(Valoration valoration) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(valoration);
        session.getTransaction().commit();
        session.close();
    }
    
    public boolean valorationsExist(List<Valoration> valorations) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try{
	        for(Valoration valoration : valorations) {
	        	Query q = session.createQuery("from Valoration where idChallenge = " + 
										       valoration.getIdChallenge() + 
										       " and idUser = " + 
										       valoration.getIdUser() +
										       " and idValorator = " +
										       valoration.getIdValorator());
	        	if(q.uniqueResult() != null) {
	        		return true;
	        	}
	        }
	        return false;
        } finally {
        	session.getTransaction().commit();
            session.close();
        }
    }
    
    public float getAverageFromUser(int userId) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createSQLQuery("SELECT AVG(challenge_accepted.valoration.valoration) " +
                "FROM challenge_accepted.valoration " +
                "WHERE challenge_accepted.valoration.id_user = " + userId);
        BigDecimal result = (BigDecimal)q.uniqueResult();
        session.getTransaction().commit();
        session.close();

        return result.floatValue();
    }
}
