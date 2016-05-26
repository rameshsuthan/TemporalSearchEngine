package edu.utdallas.fileio;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Bean Class to store the statistics for the particular file
 * @author ramesh
 *
 */
public class FileStats {
	private String fileName;
	private File file;
	private HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
	private HashSet<String> temporalSet=new HashSet<String>();
	private long totalTokens=0;
	private long uniqueTokens=0;
	
	public FileStats(File file) {
		super();
		this.file=file;
		this.fileName=file.getName();
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub-
		String str="[fileName = "+ fileName;
		str+="\tTotalTokens = "+ totalTokens;
		str+="\tuniqueTokens = "+ uniqueTokens;
		str+="\ttokenMap]";
		
		return str;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public HashSet<String> getTemporalSet() {
		return temporalSet;
	}

	public void setTemporalSet(HashSet<String> temporalSet) {
		this.temporalSet = temporalSet;
	}
}
