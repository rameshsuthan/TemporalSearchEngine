package edu.utdallas.fileio;

import java.util.HashMap;

/**
 * Bean Class to store the Summary statistics for the tokens/Stems
 * @author ramesh
 *
 */
public class TokenStats {
	private HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
	private long totalTokens=0;
	private long uniqueTokens=0;
	private double avgUniqueTokens;
	
	public HashMap<String, Integer> getTokenMap() {
		return tokenMap;
	}
	public void setTokenMap(HashMap<String, Integer> tokenMap) {
		this.tokenMap = tokenMap;
	}
	public long getTotalTokens() {
		return totalTokens;
	}
	public void setTotalTokens(long totalTokens) {
		this.totalTokens = totalTokens;
	}
	public long getUniqueTokens() {
		return uniqueTokens;
	}
	public void setUniqueTokens(long uniqueTokens) {
		this.uniqueTokens = uniqueTokens;
	}
	
	public double getAvgUniqueTokens() {
		return avgUniqueTokens;
	}
	public void setAvgUniqueTokens(double avgUniqueTokens) {
		this.avgUniqueTokens = avgUniqueTokens;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="[\tTotalTokens = "+ totalTokens;
		str+="\tuniqueTokens = "+ uniqueTokens;
		str+="\tavgUniqueTokens = "+ avgUniqueTokens;
		str+="\ttokenMap" + tokenMap+"]";
		return str;
	}
	
	
}
