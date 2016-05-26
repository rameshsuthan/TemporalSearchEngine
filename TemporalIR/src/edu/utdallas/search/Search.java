package edu.utdallas.search;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

public class Search {
	private ArrayList<SearchResult> searchResultList;
	private String query;
	private static MainProcess mainProcess;
	private String path;
	private Integer noOfResults=10;

	public Search() {
		path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/");
		System.out.println("Context path:" + path);
		if (mainProcess == null) {
			mainProcess = new MainProcess();
			mainProcess.initialize(path + "resources/data/files/", path
					+ "resources/data/",path+"resources/data/Index/");
		}

	}

	public String search() {
		searchResultList = mainProcess.startSearch(query,noOfResults);
		System.out.println(searchResultList);
		return "success";

	}

	public ArrayList<SearchResult> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(ArrayList<SearchResult> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public MainProcess getMainProcess() {
		return mainProcess;
	}

	public void setMainProcess(MainProcess mainProcess) {
		this.mainProcess = mainProcess;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getNoOfResults() {
		return noOfResults;
	}

	public void setNoOfResults(Integer noOfResults) {
		this.noOfResults = noOfResults;
	}

	public static void main(String[] args) {
		new Search().search();
	}

}
