package edu.utdallas.data;

public class SampleKeyData {
	private String actualTerm;
	private String term;
	private long docFeq;
	private long termFreq;
	private long invertedListLength;
	
	

	public SampleKeyData(String actualTerm, String term) {
		super();
		this.actualTerm = actualTerm;
		this.term = term;
	}

	public long getDocFeq() {
		return docFeq;
	}

	public void setDocFeq(long docFeq) {
		this.docFeq = docFeq;
	}

	public long getTermFreq() {
		return termFreq;
	}

	public void setTermFreq(long termFreq) {
		this.termFreq = termFreq;
	}

	public long getInvertedListLength() {
		return invertedListLength;
	}

	public void setInvertedListLength(long invertedListLength) {
		this.invertedListLength = invertedListLength;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getActualTerm() {
		return actualTerm;
	}

	public void setActualTerm(String actualTerm) {
		this.actualTerm = actualTerm;
	}


}
