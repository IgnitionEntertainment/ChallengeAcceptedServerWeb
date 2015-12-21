package com.ignitionentertainment.utils.helpers;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.PendingChange;
import com.ignitionentertainment.utils.HibernateUtil;

public class PendingChangeHelper {
    Session session = null;
    
    public PendingChangeHelper(){
    }
    
    public void createPendingChallenge(PendingChange pendingChange) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(pendingChange);
        session.getTransaction().commit();
        session.close();
    }
    
    public PendingChange getPendingChangeById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("from PendingChange where id = " + id);
        PendingChange pendingChange = (PendingChange)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return pendingChange;
    }
    
    public void updatePendingChange(PendingChange pendingChange) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(pendingChange);
        session.getTransaction().commit();
        session.close();
    }
    
    public void deletePendingChange(PendingChange pendingChange) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(pendingChange);
        session.getTransaction().commit();
        session.close();
    }
    
    public List<PendingChange> getChangesByUserId(int userId) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query getPendingChanges = session.createQuery("from PendingChange where id_user = " + userId);
        List<PendingChange> pendingChanges = (List<PendingChange>)getPendingChanges.list();
        session.getTransaction().commit();
        session.close();

        return pendingChanges;
    }
    
    public void deletePendingChangesByUserId(int userId) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query deletePendingChanges = session.createQuery("delete PendingChange where id_user = " + userId);
        deletePendingChanges.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
