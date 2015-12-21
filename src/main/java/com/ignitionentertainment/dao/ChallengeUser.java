package com.ignitionentertainment.dao;
// Generated 09-dic-2015 23:36:54 by Hibernate Tools 4.3.1

/**
 * ChallengeUser generated by hbm2java
 */
public class ChallengeUser  implements java.io.Serializable {


     private int id;
     private Integer idChallenge;
     private Integer idUser;

    public ChallengeUser() {
    }

    public ChallengeUser(int id, Integer idChallenge, Integer idUser) {
       this.id = id;
       this.idChallenge = idChallenge;
       this.idUser = idUser;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getIdChallenge() {
		return idChallenge;
	}

	public void setIdChallenge(Integer idChallenge) {
		this.idChallenge = idChallenge;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
    public static class ChallengeUserBuilder {
    	private int id;
        private Integer idChallenge;
        private Integer idUser;

    	public ChallengeUserBuilder(){
    	}
       
    	public ChallengeUserBuilder setId(int id) {
    		this.id = id;
    		return this;
    	}
    	
    	public ChallengeUserBuilder setIdChallenge(Integer idChallenge) {
    		this.idChallenge = idChallenge;
    		return this;
    	}
    	
    	public ChallengeUserBuilder setIdUser(Integer idUser) {
    		this.idUser = idUser;
    		return this;
    	}
    	
    	public ChallengeUser Build() {
    		return new ChallengeUser(id, idChallenge, idUser);
    	}
    }

}


