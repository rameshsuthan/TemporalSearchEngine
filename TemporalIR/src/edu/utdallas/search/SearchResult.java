package edu.utdallas.search;

public class SearchResult {
	private int rank;
	private double score;
	private String url;
	private String fileData;
	private String fileName;
	
	public SearchResult(int rank, double score, String url, String fileData,
			String fileName) {
		super();
		this.rank = rank;
		this.score = score;
		this.url = url;
		this.fileData = fileData;
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String str=rank + "|"+ score+"|"+url+"|"+fileName+"|"+fileData;
		return str;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		rank = rank;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileData() {
		return fileData;
	}

	public void setFileData(String fileData) {
		this.fileData = fileData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	

}
