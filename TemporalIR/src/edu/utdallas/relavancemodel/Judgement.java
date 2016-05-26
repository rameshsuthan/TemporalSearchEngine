package edu.utdallas.relavancemodel;

public class Judgement {
	private String qId;
	private String urlNo;
	private String pNo;
	private boolean relevance;
	
	
	public Judgement(String qId, String urlNo, String pNo, boolean relevance) {
		super();
		this.qId = qId;
		this.urlNo = urlNo;
		this.pNo = pNo;
		this.relevance = relevance;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub-
		String str="[urlNo = "+ urlNo;
		str+="\tqId = "+ qId;
		str+="\tpNo = "+ pNo;
		str+="\trelevance ="+relevance +"]";
		
		return str;
	}
	
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public String getUrlNo() {
		return urlNo;
	}
	public void setUrlNo(String urlNo) {
		this.urlNo = urlNo;
	}
	
	public boolean isRelevance() {
		return relevance;
	}
	public void setRelevance(boolean relevance) {
		this.relevance = relevance;
	}
	
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

}
