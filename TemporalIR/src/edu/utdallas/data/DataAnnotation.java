package edu.utdallas.data;

import java.util.HashMap;

public class DataAnnotation {
	
	private String urlNo;
	private String pNo;
	private HashMap<String,String> rfnMap = new HashMap<String, String>();
		
	public DataAnnotation(String urlNo, String pNo) {
		super();
		this.urlNo = urlNo;
		this.pNo = pNo;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub-
		String str="[urlNo = "+ urlNo;
		str+="\tpNo = "+ pNo;
		str+="\trfn ="+rfnMap +"]";
		
		return str;
	}

	
	public String getUrlNo() {
		return urlNo;
	}
	
	public void setUrlNo(String urlNo) {
		this.urlNo = urlNo;
	}
	public String getpNo() {
		return pNo;
	}
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}


	public HashMap<String, String> getRfnMap() {
		return rfnMap;
	}


	public void setRfnMap(HashMap<String, String> rfnMap) {
		this.rfnMap = rfnMap;
	}
	
	
	

}
