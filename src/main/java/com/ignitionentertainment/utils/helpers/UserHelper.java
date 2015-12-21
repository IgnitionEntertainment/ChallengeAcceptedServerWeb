/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Tag;
import com.ignitionentertainment.dao.User;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class UserHelper {
    Session session = null;
    
    public UserHelper(){
    }
    
    public User getUserById(int id){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where id = "+String.valueOf(id));
        User result = (User)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

	public void createUser(User user) {
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
		
	}

	public User findUserByEmail(String email) {
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where email='"+email+"'");
        User result = (User)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
		return result;
	}

	public User findUserByUsername(String username) {
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where username='"+username+"'");
        User result = (User)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
		return result;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from User where username='"+username+"' and password = '"+password+"'");
        User result = (User)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
		return result;
	}

	public List<User> getUsersOrderedByScore() {
		List<User> userList = null;
		session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("SELECT u FROM User as u, Score as s WHERE u.idScore = s.id ORDER BY s.totalScore");
        userList = (List<User>) q.list();
        session.getTransaction().commit();
        session.close();
		
		return userList;
	}
}
