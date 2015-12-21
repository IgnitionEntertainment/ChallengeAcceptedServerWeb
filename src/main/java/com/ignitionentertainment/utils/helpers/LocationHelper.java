/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ignitionentertainment.utils.helpers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ignitionentertainment.dao.Challenge;
import com.ignitionentertainment.dao.Location;
import com.ignitionentertainment.utils.HibernateUtil;

/**
 *
 * @author svila
 */
public class LocationHelper {
    Session session = null;
    
    public LocationHelper(){
    }
    
    public int createLocation(Location location){
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("INSERT INTO challenge_accepted.location (geog) VALUES ("+
        			"ST_GeographyFromText('SRID=4326;POINT(" + location.getLongitude() +
        			" " + location.getLatitude() + ")')) RETURNING id");
        int locationId = (int) q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return locationId;
    }
    
    public Location getLocationById(int id){
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        // It should works, but here fails
        //Query q = session.createQuery("SELECT ST_X(geog::geometry), ST_Y(geog::geometry) FROM challenge_accepted.location WHERE id = " + id);
        
        Query q = session.createSQLQuery("SELECT ST_AsText(geog) FROM challenge_accepted.location WHERE id = " + id);
        String result = (String)q.uniqueResult();
        session.getTransaction().commit();
        session.close();
        
        Pattern pattern = Pattern.compile("\\d+(?:\\.\\d+)?");
        Matcher matcher = pattern.matcher(result);
        matcher.find();
        Double longitude = Double.valueOf(matcher.group());
        matcher.find();
        Double latitude = Double.valueOf(matcher.group());
        
        Location location = new Location.LocationBuilder()
        		.setId(id)
        		.setLongitude(longitude)
        		.setLatitude(latitude)
        		.Build();
        return location;
    }
    
    public void updateLocation(Location location) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createSQLQuery("UPDATE challenge_accepted.location SET geog = ST_GeographyFromText('SRID=4326;POINT(" +
        location.getLongitude() +" " + location.getLatitude() + ")') WHERE id = " + location.getId());
        q.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
    
    public List<Integer> getChallengeIdsNearToLocation(Location location, int meters) {
    	session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query q = session.createSQLQuery("SELECT challenge_accepted.challenge.id FROM challenge_accepted.location, challenge_accepted.challenge WHERE " +
                "ST_DWithin(challenge_accepted.location.geog, ST_GeographyFromText('SRID=4326;POINT("+ location.getLongitude() +" " +
                location.getLatitude() + ")'), " + meters + ") and " +
                "challenge_accepted.challenge.id_location=challenge_accepted.location.id");
        
        List<Integer> challengeIds = (List<Integer>)q.list();
        session.getTransaction().commit();
        session.close();
    	return challengeIds;
    }
}
