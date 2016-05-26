package edu.utdallas.data;

import java.io.Serializable;

public class DocStats implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private long max_tf;
	private long doclen;

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
	public DocStats(String fileName, long max_tf, long doclen) {
		super();
		this.fileName = fileName;
		this.max_tf = max_tf;
		this.doclen = doclen;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
