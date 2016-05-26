package edu.utdallas.search;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.utdallas.data.DataAnnotation;
import edu.utdallas.data.DocStats;
import edu.utdallas.data.ReadRelevanceData;
import edu.utdallas.fileio.FileProcessor;
import edu.utdallas.fileio.ReadTitle;
import edu.utdallas.fileio.Utility;
import edu.utdallas.index.DictionaryValueEntry;
import edu.utdallas.index.InvertedIndexVersion1;
import edu.utdallas.query.Query;
import edu.utdallas.relavancemodel.Judgement;
import edu.utdallas.relavancemodel.StoredQuery;
import edu.utdallas.relavancemodel.TemporalAnnotator;

public class RankedSearch {

	private double scoreW1[];

	private HashMap<Long, Double> W1ScoreMap = new HashMap<Long, Double>();
	private static double avgDocLen = 0.0;
	private FileProcessor fileProcessor;
	InvertedIndexVersion1 index;
	private static double temporalBaseScore=0.1;

	public RankedSearch(InvertedIndexVersion1 index,
			FileProcessor fileProcessor) {
		this.index = index;
		this.fileProcessor = fileProcessor;
		findAvgDoclen(index);

	}

	public void findAvgDoclen(InvertedIndexVersion1 index) {
		for (Entry<Long, DocStats> docStatsSet : index.getDocStatMap()
				.entrySet()) {

			avgDocLen = avgDocLen + docStatsSet.getValue().getDoclen();

		}

		avgDocLen = avgDocLen / index.getDocStatMap().size();
	}

	public void calculateRank(Query query, ReadRelevanceData readRelevanceData) {
		calculateW1Scores(query);
		try{
		updateRelevanceScore(query, readRelevanceData);
		updateTemporalScore(query);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Skip relvance score");
		}

	}

	public void calculateW1Scores(Query query) {

		TreeMap<String, DictionaryValueEntry> dict = index.getDictionary();
		HashMap<Long, DocStats> docStatMap = index.getDocStatMap();
		int collectionSize = docStatMap.size();
		// System.out.println(dict);

		for (Entry<String, Integer> entrySet : query.getQueryMap().entrySet()) {
			String qTerm = entrySet.getKey();
			int qTermFreq = entrySet.getValue();

			DictionaryValueEntry dictEntry = dict.get(qTerm);
			//System.out.println(qTerm+" "+dictEntry);
			

			if (dictEntry == null) {
				// term not found in the dictionary
				continue;
			}

			ArrayList<Long> postingList = dictEntry.getPostingList();
			ArrayList<Integer> termFreqList = dictEntry.getTermFreqList();
			long df = dictEntry.getDocFreq();

			for (int i = 0; i < postingList.size(); i++) {

				long docId = postingList.get(i);
				long tf = termFreqList.get(i);
				long maxtf = docStatMap.get(docId).getMax_tf();

				double W1Score = (0.4 + 0.6 * Math.log10(tf + 0.5)
						/ Math.log10(maxtf + 1.0))
						* (Math.log10(collectionSize / df) / Math
								.log10(collectionSize));
				//System.out.println(W1Score);
				//System.out.println(tf+" "+df+ " "+collectionSize+" "+maxtf);
				Double val;
				if ((val = W1ScoreMap.get(docId)) == null) {
					W1ScoreMap.put(docId, W1Score);
				} else {
					W1ScoreMap.put(docId, W1Score + val);

				}

			}
		}

	}

	public void updateRelevanceScore(Query query,
			ReadRelevanceData readRelevanceData) {
		// matching with the stored query
		System.out.println("Updating Relevance Score");
		StoredQuery storedQuery;
		double relScore;
		if ((storedQuery = readRelevanceData.getStoredQMap().get(
				query.getQueryStr())) != null) {
			String qId = storedQuery.getqId();
			System.out.println("Matching qid" + qId);

			ArrayList<Judgement> judgList;
			if ((judgList = readRelevanceData.getJudgementMap().get(qId)) != null) {

				for (Judgement judgement : judgList) {
					if (judgement.isRelevance()) {

						DataAnnotation annotation;
						relScore = 0.0;
						if ((annotation = readRelevanceData.getAnnotationMap()
								.get(judgement.getpNo())) != null) {
							relScore += annotation.getRfnMap().size() * 0.2;
							
							Double val;
							
							System.out.println(fileProcessor.getFileNametoIndexMap());
							String pNo=judgement.getpNo();
							while(pNo.length()<5){
								pNo=0+pNo;
							}

							System.out.println("updates score for doc"
									+ pNo + " adding " + relScore);

							Integer docIdint=fileProcessor.getFileNametoIndexMap().get(pNo+".txt");
							long docId;
							
							if(docIdint!=null){
								docId=docIdint;
							}else{
								continue;
							}
							//Long docId = Long.parseLong(judgement.getpNo());
							
							if ((val = W1ScoreMap.get(docId)) == null) {
								W1ScoreMap.put(docId, relScore);
							} else {
								W1ScoreMap.put(docId, val + relScore);

							}

						}

					}

				}
			}

		}

	}
	
	public void updateTemporalScore(Query query){
		
		System.out.println("Updating temporal Score");
		TemporalAnnotator temporalAnnotator=new TemporalAnnotator();
		ArrayList<String> temporalAnnList = temporalAnnotator.annotate(query.getQueryStr());
		
		for(String temporalAnn:temporalAnnList){
			for(Long temp:index.getTemporalMap().get(temporalAnn)){
				
				long temporalDocId=temp;
				System.out.println(temporalDocId);
				Double val;
				System.out.println("updates score for doc"
						+ fileProcessor.getFileIndexMap().get((int)temporalDocId).getName() + " adding " + temporalBaseScore);

				if ((val = W1ScoreMap.get(temporalDocId)) == null) {
					W1ScoreMap.put(temporalDocId, temporalBaseScore);
				} else {
					W1ScoreMap.put(temporalDocId, val + temporalBaseScore);

				}

				
			}
			
		}
		
	}

	public void retrieveTopNSearchW1(int n) {
		// System.out.println(W1ScoreMap.size());
		// System.out.println(W1ScoreMap);
		LinkedList<Entry<Long, Double>> rankedList = Utility
				.mapTosortedScoreList(W1ScoreMap);

		if (rankedList.size() < n) {
			n = rankedList.size();
		}

		System.out.println("\nResults based on W1");
		System.out.println("===================");
		System.out.format("%-5s%-20s%-20s%-20s\n", "Rank", "Doc_Identifier",
				"Score", "Heading");
		System.out
				.println("----------------------------------------------------------------");

		// System.out.format("%s|%s|%s|%s\n","Rank","Doc_Identifier","Score","Heading");

		for (int i = 0; i < n; i++) {
			Entry<Long, Double> rankedEntry = rankedList.get(i);
			long docId = rankedEntry.getKey();
			double score = rankedEntry.getValue();

			File file = fileProcessor.getFileIndexMap().get((int)docId);

			String title = ReadTitle.readFile(file.getAbsolutePath());
			title = title.replace("\n", " ");
			// System.out.println((i+1)+" "+file.getName()+" "+ score + " "+
			// title.trim() );

			// System.out.format("%-5s%-20s%-20s%-20s\n",(i+1),file.getName(),score,title.trim());
			System.out.format("%-5s%-20s%-20s%-20s\n", (i + 1), file.getName(),
					score, title.trim());
			// System.out.format("%s|%s|%s|%s|%s\n",(i+1),file.getName(),score,title.trim(),file.getAbsolutePath());
		}

	}

	public ArrayList<SearchResult> search(Query query, int n) {

		ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();

		LinkedList<Entry<Long, Double>> rankedList = Utility
				.mapTosortedScoreList(W1ScoreMap);

		if (rankedList.size() < n) {
			n = rankedList.size();
		}

		// System.out.format("%s|%s|%s|%s\n","Rank","Doc_Identifier","Score","Heading");

		for (int i = 0; i < n; i++) {
			Entry<Long, Double> rankedEntry = rankedList.get(i);
			long docId = rankedEntry.getKey();
			double score = rankedEntry.getValue();

//			File file = fileStatsList.get((int) docId - 1).getFile();
			File file = fileProcessor.getFileIndexMap().get((int)docId);


			String title = ReadTitle.readFile(file.getAbsolutePath());
			title = title.replace("\n", " ");
			// System.out.println((i+1)+" "+file.getName()+" "+ score + " "+
			// title.trim() );

			// System.out.format("%-5s%-20s%-20s%-20s\n",(i+1),file.getName(),score,title.trim());
			// System.out.format("%-5s%-20s%-20s%-20s\n",(i+1),file.getName(),score,title.trim());
			SearchResult searchResult = new SearchResult((i + 1), score, "",
					title.trim(), file.getName());
			searchResultsList.add(searchResult);
			// System.out.format("%s|%s|%s|%s|%s\n",(i+1),file.getName(),score,title.trim(),file.getAbsolutePath());
		}

		return searchResultsList;
	}

}
