package edu.utdallas.lemmatize;

import java.util.HashMap;
import java.util.HashSet;

public class LemmaFileStats {
	private String fileName;
	private long fileIndex;
	private long max_tf;
	private long doclen;
	private HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
	private HashSet<String> temporalSet=new HashSet<String>();
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="Map: "+tokenMap+"\n";
		str+="fileIndex: "+fileIndex+"\n";
		str+="fileName: "+fileName+"\n";
		str+="doclen: "+doclen+"\n";
		str+="max_tf: "+max_tf+"\n";
		return str;
	}

	
	public HashMap<String, Integer> getTokenMap() {
		return tokenMap;
	}
	public void setTokenMap(HashMap<String, Integer> tokenMap) {
		this.tokenMap = tokenMap;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileIndex() {
		return fileIndex;
	}
	public void setFileIndex(long fileIndex) {
		this.fileIndex = fileIndex;
	}
	public long getMax_tf() {
		return max_tf;
	}
	public void setMax_tf(long max_tf) {
		this.max_tf = max_tf;
	}
	public long getDoclen() {
		return doclen;
	}
	public void setDoclen(long doclen) {
		this.doclen = doclen;
	}


	public HashSet<String> getTemporalSet() {
		return temporalSet;
	}


	public void setTemporalSet(HashSet<String> temporalSet) {
		this.temporalSet = temporalSet;
	}
	

}
