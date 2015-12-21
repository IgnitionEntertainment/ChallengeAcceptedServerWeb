package com.ignitionentertainment.dao;
// Generated 09-dic-2015 23:36:54 by Hibernate Tools 4.3.1

/**
 * Valoration generated by hbm2java
 */
public class Valoration  implements java.io.Serializable {


     private int id;
     private Integer idChallenge;
     private Integer idUser;
     private Integer idValorator;
     private Integer valoration;

    public Valoration() {
    }

	public Valoration(int id, Integer idChallenge, Integer idUser, Integer idValorator, Integer valoration) {
		this.id = id;
		this.idChallenge = idChallenge;
		this.idUser = idUser;
		this.idValorator = idValorator;
		this.valoration = valoration;
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

	public Integer getIdValorator() {
		return idValorator;
	}

	public void setIdValorator(Integer idValorator) {
		this.idValorator = idValorator;
	}

	public Integer getValoration() {
		return valoration;
	}

	public void setValoration(Integer valoration) {
		this.valoration = valoration;
	}
	
    public static class ValorationBuilder {
    	private int id;
        private Integer idChallenge;
        private Integer idUser;
        private Integer idValorator;
        private Integer valoration;

    	public ValorationBuilder(){
    	}
       
    	public ValorationBuilder setId(int id) {
    		this.id = id;
    		return this;
    	}
    	
    	public ValorationBuilder setIdChallenge(Integer idChallenge) {
    		this.idChallenge = idChallenge;
    		return this;
    	}
    	
    	public ValorationBuilder setIdUser(Integer idUser) {
    		this.idUser = idUser;
    		return this;
    	}
    	
    	public ValorationBuilder setIdValorator(Integer idValorator) {
    		this.idValorator = idValorator;
    		return this;
    	}
    	
    	public ValorationBuilder setValoration(Integer valoration) {
    		this.valoration = valoration;
    		return this;
    	}

    	public Valoration Build() {
    		return new Valoration(id, idChallenge, idUser, idValorator, valoration);
    	}
    }

}


