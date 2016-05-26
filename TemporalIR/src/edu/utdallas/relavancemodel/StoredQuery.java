package edu.utdallas.relavancemodel;

public class StoredQuery {
	String qId;
	String informationNeed;
	String query;
	
	
	public StoredQuery(String qId, String informationNeed, String query) {
		super();
		this.qId = qId;
		this.informationNeed = informationNeed;
		this.query = query;
	}
	
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public String getInformationNeed() {
		return informationNeed;
	}
	public void setInformationNeed(String informationNeed) {
		this.informationNeed = informationNeed;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub-
		String str="[qId = "+ qId;
		str+="\tinformationNeed = "+ informationNeed;
		str+="\tquery = "+ query+"]";
		
		return str;
	}

	
	

}
