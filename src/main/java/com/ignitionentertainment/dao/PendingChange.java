package com.ignitionentertainment.dao;

public class PendingChange  implements java.io.Serializable {

    private int id;
    private Integer idChallenge;
    private Integer idUser;
    private Integer change;

   public PendingChange() {
   }

   public PendingChange(int id, Integer idChallenge, Integer idUser, Integer change) {
      this.id = id;
      this.idChallenge = idChallenge;
      this.idUser = idUser;
      this.change = change;
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
	
	public Integer getChange() {
		return change;
	}

	public void setChange(Integer change) {
		this.change = change;
	}
	
   public static class PendingChangeBuilder {
   	private int id;
    private Integer idChallenge;
    private Integer idUser;
    private Integer change;

   	public PendingChangeBuilder(){
   	}
      
   	public PendingChangeBuilder setId(int id) {
   		this.id = id;
   		return this;
   	}
   	
   	public PendingChangeBuilder setIdChallenge(Integer idChallenge) {
   		this.idChallenge = idChallenge;
   		return this;
   	}
   	
   	public PendingChangeBuilder setIdUser(Integer idUser) {
   		this.idUser = idUser;
   		return this;
   	}
   	
   	public PendingChangeBuilder setChange(Integer change) {
   		this.change = change;
   		return this;
   	}
   	
   	public PendingChange Build() {
   		return new PendingChange(id, idChallenge, idUser, change);
   	}
   }

}
