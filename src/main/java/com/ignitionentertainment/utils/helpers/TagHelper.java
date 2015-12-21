/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ignitionentertainment.dao.ChallengeTag;
import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author JPPL
 */
public class TagHelper {
    
    Session session = null;
    
    public TagHelper(){
    }
    
    public List<Tag> getAllTags(){
        List<Tag> tagList = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Tag");
            tagList = (List<Tag>) q.list();
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return tagList;
    }
    
    public Tag getTagById(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from Tag where id = "+String.valueOf(id));
        Tag result = (Tag)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    public void updateTag(Tag tag){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(tag);
        session.getTransaction().commit();
        session.close();
    }

    public int createTag(Tag tag) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.tag (tag) VALUES ('" + tag.getTag() + "') RETURNING id");
        int tagId = (int) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return tagId;
    }

    public void deleteTag(Tag tag) {
    	ChallengeTagHelper challengeTagHelper = new ChallengeTagHelper();
    	List<ChallengeTag> challengeTags = challengeTagHelper.getChallengeTagsByTagId(tag.getId());
    	for(ChallengeTag challengeTag : challengeTags) {
    		challengeTagHelper.deleteChallengeTag(challengeTag);
    	}
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(tag);
        session.getTransaction().commit();
        session.close();
    }
    
    public Tag getTagByName(String name) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        //Query q = session.createQuery("from Tag where tag = " + name);
        //Tag tag = (Tag) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return null;
    }
    
}
