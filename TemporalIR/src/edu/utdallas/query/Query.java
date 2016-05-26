package edu.utdallas.query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import edu.utdallas.fileio.StopWords;
import edu.utdallas.lemmatize.Lemmatizer;

public class Query {
	private String queryStr;
	private LinkedHashMap<String, Integer> queryMap=new LinkedHashMap<String, Integer>();
	private static Lemmatizer lemmatizer=new Lemmatizer();
	private String indexedQueryStr="";
	
	public Query(String queryStr){
		this.queryStr=queryStr;
		tokenizeQuery();
		
	}
	
	public void tokenizeQuery(){
		
		String[] tokens = queryStr.split("\\s+|\\\\|\\-|\\/");
		Integer count = null;
		long totalTokens = 0;
		

		for (String token : tokens) {
			token = token.trim();

			token = token.replaceAll("'s", "");
			if (token.contains(".")) {
				// check '.' if it is between number eg) 8.26 or if it is reference A1.25
				if (!token.matches("([a-zA-Z0-9]*)(\\.)(\\d+)(.*)")) {
					//Delete the '.' if it doesn't satisfy the above criteria 
					token = token.replaceAll("[.]", "");

				}
				
				
			} 
			
			// Replacing the special characters except '.' 
			token=token.replaceAll("[^\\w.]","");

			if (token.isEmpty()) {
				continue;
			}

			token = token.toLowerCase();
			token=lemmatizer.lemmatize(token).get(0);
			if ((count = queryMap.get(token)) == null) {
				queryMap.put(token.trim(), 1);
			} else {
				queryMap.put(token.trim(), count + 1);
			}
			totalTokens++;	
		}
		
		for(String stopword:StopWords.stopWordList){
			//stopword=fixedLengthString(stopword, MaxKeyLen);
			if(queryMap.remove(stopword.toLowerCase())!=null){
				//System.out.println(stopword+"Removed");
			}
		}
			Iterator<String> iterator = queryMap.keySet().iterator();
			while(iterator.hasNext()){
				indexedQueryStr+=iterator.next()+" ";
			}
		
		
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public LinkedHashMap<String, Integer> getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(LinkedHashMap<String, Integer> queryMap) {
		this.queryMap = queryMap;
	}

	public static Lemmatizer getLemmatizer() {
		return lemmatizer;
	}

	public static void setLemmatizer(Lemmatizer lemmatizer) {
		Query.lemmatizer = lemmatizer;
	}

	public String getIndexedQueryStr() {
		return indexedQueryStr;
	}

	public void setIndexedQueryStr(String indexedQueryStr) {
		this.indexedQueryStr = indexedQueryStr;
	}

}
