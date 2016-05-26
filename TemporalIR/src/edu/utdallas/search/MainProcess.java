package edu.utdallas.search;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.utdallas.data.ReadRelevanceData;
import edu.utdallas.fileio.FileProcessor;
import edu.utdallas.fileio.FileUtil;
import edu.utdallas.index.InvertedIndexVersion1;
import edu.utdallas.lemmatize.LemmatizerProcess;
import edu.utdallas.query.Query;

/**
 * Main Class for the project does the following 1. Tokenizing the files 2.
 * Stemming the tokens
 * 
 * @author ramesh
 *
 */
public class MainProcess {
	private InvertedIndexVersion1 invertedIndexVersion1;
	private FileProcessor fileProcessor;
	private boolean initSuccess = false;
	private ReadRelevanceData readRelevanceData;
	private String indexDir;

	public int initialize(String collectionDir, String relvanceDataDir,
			String indexDir) {

		this.indexDir = indexDir;
		if (!initSuccess) {

			int noOfThreads = 1;

			if (!FileUtil.isDirPresent(collectionDir)) {
				System.err
						.println("Exiting the program Directory doesn't exist");
				return -1;
			}

			String uncompressedVer1File = indexDir + "Index1file.idx";
			long startTime, endTime;
			System.out
					.println("-----------------------Tokenization--------------------------------------\n");
			long totStartTime = System.currentTimeMillis();
			fileProcessor = new FileProcessor(collectionDir, indexDir);

			fileProcessor.startProcess(noOfThreads);
			long totEndTime = System.currentTimeMillis();

			System.out.println("\n Time Taken to tokenize the Document:"
					+ (totEndTime - totStartTime) + " ms");

			// Lemmatizing process Starts here(Index Version 1)
			System.out
					.println("\n**************************************************************************");
			System.out
					.println("\n----------------------Version1(Lemmatizing)--------------------------------------");
			startTime = System.currentTimeMillis();
			
			//Load the previous version if present
			invertedIndexVersion1=InvertedIndexVersion1.loadInvertedIndex(uncompressedVer1File);
			
			//if no new files are there to index skip the process
			if (!fileProcessor.isSkipIndexing()) {
			LemmatizerProcess lemmatizerProcess = new LemmatizerProcess();
			lemmatizerProcess.start(fileProcessor);
			
			if(invertedIndexVersion1==null){
				invertedIndexVersion1 = new InvertedIndexVersion1();
			}
			
			InvertedIndexVersion1.setMaxKeyLen(lemmatizerProcess
					.getCurrMaxKeyLen());
			invertedIndexVersion1.setUnCompressedFileName(uncompressedVer1File);

			// Generate uncompressed index for version 1
			
				invertedIndexVersion1
						.generateInvertedIndexForLemma(lemmatizerProcess
								.getLemmaFileStatsList());
			}
			
			
			endTime = System.currentTimeMillis();
			System.out
					.println("\nTime Taken to generate UnCompressed Version1:"
							+ (endTime - startTime) + " ms");

			readRelevanceData = new ReadRelevanceData();
			readRelevanceData.urlCSV(relvanceDataDir + "url.csv");
			readRelevanceData.queryCSV(relvanceDataDir + "queries.csv");
			readRelevanceData
					.annotationCSV(relvanceDataDir + "annotations.csv");
			readRelevanceData.judgementCSV(relvanceDataDir + "judgements.csv");
			initSuccess = true;
		}
		return 0;
	}

	public ArrayList<SearchResult> startSearch(String queryStr, Integer noOfResults) {

		if (!initSuccess) {
			return null;
		}

		int qCount = 0;

		Query query = new Query(queryStr);
		System.out.println("Query         : " + query.getQueryStr());
		System.out.println("Indexed Query : " + query.getIndexedQueryStr());

		RankedSearch rankedSearch = new RankedSearch(invertedIndexVersion1,
				fileProcessor);
		rankedSearch.calculateRank(query, readRelevanceData);
		rankedSearch.retrieveTopNSearchW1(noOfResults);

		ArrayList<SearchResult> searchResultList = rankedSearch.search(query,
				noOfResults);
		System.out
				.println("*******************************************************************************************************************/n");
		return searchResultList;

	}

}
