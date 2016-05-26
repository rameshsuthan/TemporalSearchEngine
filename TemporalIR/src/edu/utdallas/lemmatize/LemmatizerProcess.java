package edu.utdallas.lemmatize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import edu.utdallas.fileio.FileProcessor;
import edu.utdallas.fileio.FileStats;
import edu.utdallas.fileio.TokenStats;

/**
 * This class does the Lemmatizing process
 * @author ramesh
 * 
 */
public class LemmatizerProcess {

	private TokenStats tokenLemmaStats;
	private Lemmatizer lemmatizer=new Lemmatizer();
	private ArrayList<LemmaFileStats> lemmaFileStatsList = new ArrayList<LemmaFileStats>();
	private int currMaxKeyLen=24;

	public LemmatizerProcess() {

	}

	


	/** 
	 * This function compute the statistics for the stem
	 * @param fileStats
	 * @return
	 */
	public LemmaFileStats computeLemmaStats(FileStats fileStats,int fileIndex) {
		
		LemmaFileStats lemmaFileStats= new LemmaFileStats();
		lemmaFileStats.setFileName(fileStats.getFileName());
		lemmaFileStats.setFileIndex(fileIndex);
		
		HashMap<String, Integer> lemmaTokenMap = lemmaFileStats.getTokenMap();
		int totFreq = 0;

		for (Entry<String, Integer> tokenEntry : fileStats.getTokenMap()
				.entrySet()) {

			String token = tokenEntry.getKey();
			int count = tokenEntry.getValue();
			
			String lemma = lemmatizer.lemmatize(token).get(0);
			currMaxKeyLen=Math.max(lemma.length(),currMaxKeyLen);

			Integer totCount;

			if ((totCount = lemmaTokenMap.get(lemma)) == null) {
				lemmaTokenMap.put(lemma, count);
				totFreq += count;
			} else {
				// System.out.println("Dupl:"+token+" Count: "+totCount);
				lemmaTokenMap.put(lemma, totCount + count);
				totFreq += count;
			}

		}
		//System.out.println("totfreq"+totFreq);
		lemmaFileStats.setDoclen(fileStats.getTotalTokens());
		lemmaFileStats.setMax_tf(Collections.max(lemmaTokenMap.values()));
		lemmaFileStats.setTemporalSet(fileStats.getTemporalSet());
		// System.out.println(stemTokenMap);
		// System.out.println(stemTokenMap.size());
		// System.out.println(totFreq);
		return lemmaFileStats;
	}

	/**This function calls the stanford lemmatizer implementation for each file 
	 * @param fileProcessor
	 * @return
	 */
	public int start(FileProcessor fileProcessor) {

		ArrayList<FileStats> fileStatsList = fileProcessor.getFileStatsList();

		LemmaFileStats lemmaFileStats;

		tokenLemmaStats = new TokenStats();
		int fileIndex=0;
		for (FileStats fileStats : fileStatsList) {
			fileIndex=fileProcessor.getFileNametoIndexMap().get(fileStats.getFileName());
			//fileIndex++;
			lemmaFileStats = computeLemmaStats(fileStats,fileIndex);
			lemmaFileStatsList.add(lemmaFileStats);
			//System.out.println(fileStats.getTokenMap());
			//System.out.println(lemmaFileStats.getTokenMap());
		}
		
/*		for(LemmaFileStats lemmaFileStats1:lemmaFileStatsList){
			System.out.println(lemmaFileStats1);
		}
		
*/		
		return 0;
	}




	public TokenStats getTokenLemmaStats() {
		return tokenLemmaStats;
	}




	public void setTokenLemmaStats(TokenStats tokenLemmaStats) {
		this.tokenLemmaStats = tokenLemmaStats;
	}




	public Lemmatizer getLemmatizer() {
		return lemmatizer;
	}




	public void setLemmatizer(Lemmatizer lemmatizer) {
		this.lemmatizer = lemmatizer;
	}




	public ArrayList<LemmaFileStats> getLemmaFileStatsList() {
		return lemmaFileStatsList;
	}




	public void setLemmaFileStatsList(ArrayList<LemmaFileStats> lemmaFileStatsList) {
		this.lemmaFileStatsList = lemmaFileStatsList;
	}




	public int getCurrMaxKeyLen() {
		return currMaxKeyLen;
	}




	public void setCurrMaxKeyLen(int currMaxKeyLen) {
		this.currMaxKeyLen = currMaxKeyLen;
	}

}
