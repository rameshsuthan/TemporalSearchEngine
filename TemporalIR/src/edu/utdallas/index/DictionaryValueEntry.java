package edu.utdallas.index;

import java.io.Serializable;
import java.util.ArrayList;

public class DictionaryValueEntry implements Serializable {
	private	long docFreq;
	private long termFreq;
	private ArrayList<Long> postingList= new ArrayList<Long>();
	private ArrayList<Integer> termFreqList= new ArrayList<Integer>();
	
	
	public long getDocFreq() {
		return docFreq;
	}
	public void setDocFreq(long docFreq) {
		this.docFreq = docFreq;
	}
	public long getTermFreq() {
		return termFreq;
	}
	public void setTermFreq(long termFreq) {
		this.termFreq = termFreq;
	}
	public ArrayList<Long> getPostingList() {
		return postingList;
	}
	public void setPostingList(ArrayList<Long> postingList) {
		this.postingList = postingList;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str="docFreq: "+docFreq+"\t";
		str+="termFreq: "+termFreq+"\t";
		str+="postingList: "+postingList;
		str+="termFreqList: "+termFreqList;
		return str;
	}
	public ArrayList<Integer> getTermFreqList() {
		return termFreqList;
	}
	public void setTermFreqList(ArrayList<Integer> termFreqList) {
		this.termFreqList = termFreqList;
	}
	
	
	

}
