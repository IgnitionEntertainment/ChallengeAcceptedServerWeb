/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Score;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class ScoreHelper {
    Session session = null;
    
    public ScoreHelper(){
    }
    
    public Score getScoreById(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Score where id = "+String.valueOf(id));
        Score result = (Score)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

	public int createScore() {
		Score score = new Score();
		score.init();
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.score (won_challenges,lost_challenges,total_challenges,total_score) VALUES ("+
        		Integer.valueOf(score.getWonChallenges())+","+	
        		Integer.valueOf(score.getLostChallenges())+","+
        		Integer.valueOf(score.getTotalChallenges())+","+
        		Integer.valueOf(score.getTotalScore())+
        		") RETURNING id");
        int scoreId = (int) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return scoreId;
	}
	
	public boolean updateScore(Score score) {
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(score);
        session.getTransaction().commit();
        session.close();
        return true;
	}
}
