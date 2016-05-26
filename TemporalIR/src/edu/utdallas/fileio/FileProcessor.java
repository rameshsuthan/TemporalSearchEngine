package edu.utdallas.fileio;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.utdallas.index.IndexFileManager;

/**
 * @author ramesh
 *
 */
public class FileProcessor {

	private String collectionDirectory;
	private ArrayList<FileStats> fileStatsList = new ArrayList<FileStats>();
	private TokenStats tokenStats;
	private HashMap<String, Integer> fileNametoIndexMap = new HashMap<String, Integer>();
	private HashMap<Integer, File> fileIndexMap = new HashMap<Integer, File>();
	private int fileCounter=0;
	private String idx1;
	private String idx2;
	private boolean skipIndexing;
	

	public FileProcessor(String collectionDirectory, String indexDirectory) {
		this.collectionDirectory = collectionDirectory;
		idx1 = indexDirectory + "fileNametoIndexMap.obj";
		idx2 = indexDirectory + "fileIndexMap.obj";
		if (FileUtil.isFilePresent(idx1) && FileUtil.isFilePresent(idx2)) {
			loadPrevfileNametoIndexMap(idx1);
			loadPrevfileIndexMap(idx2);
		}
		else{
			System.out.println("First time loading the collection directory");
			//createIndexMaps();
		}
	}

	public void loadPrevfileNametoIndexMap(String fileLocation) {
		fileNametoIndexMap = (HashMap<String, Integer>) IndexFileManager
				.readObjectFile(fileLocation);
		fileCounter = fileNametoIndexMap.size();
	}

	public void loadPrevfileIndexMap(String fileLocation) {
		fileIndexMap = (HashMap<Integer, File>) IndexFileManager
				.readObjectFile(fileLocation);
	}
	
	public void createIndexMaps(){
		
		File file = new File(collectionDirectory);

		if (!file.exists() || !file.isDirectory()) {
			System.err.println("Directory not present:" + collectionDirectory);
			return;
		}

		File[] files = file.listFiles();
		int counter = 1;
		for (File currfile : files) {

			fileNametoIndexMap.put(currfile.getName(), counter);
			fileIndexMap.put(counter, currfile);
			counter++;
		}
		
		IndexFileManager.writeObjectFile(idx1, fileNametoIndexMap);
		IndexFileManager.writeObjectFile(idx2, fileIndexMap);
		fileCounter=counter-1;
	}

	public File[] getFileList() {
		File file = new File(collectionDirectory);

		if (!file.exists() || !file.isDirectory()) {
			System.err.println("Directory not present:" + collectionDirectory);
			return null;
		}

		
		return file.listFiles();
	}

	/**
	 * This function prints the statistics in the console
	 */
	public void printStats() {

		HashMap<String, Integer> totalTokenMap = tokenStats.getTokenMap();
		LinkedList<Entry<String, Integer>> sortedList = Utility
				.mapTosortedList(totalTokenMap);
		// System.out.println(Utility.sortedMap(totalTokenMap));
		System.out.println("The number of tokens in the collection: "
				+ tokenStats.getTotalTokens());
		System.out.println("The number of unique words in the collection: "
				+ tokenStats.getUniqueTokens());
		System.out.println("The average number of word tokens per document: "
				+ tokenStats.getAvgUniqueTokens());
		System.out
				.println("The number of words that occur only once in the collection: "
						+ Utility.getCountTokenOccuredOnce(sortedList));
		System.out.println("The 30 most frequent words in the collection: ");
		Utility.printTopNTokens(sortedList, 30);

	}

	/**
	 * This function computes the statistics
	 */
	public void computeTotalStats() {

		long totalTokens = 0;
		double avgUniqueTokens = 0.0;
		tokenStats = new TokenStats();

		HashMap<String, Integer> totalTokenMap = tokenStats.getTokenMap();

		for (FileStats fileStats : fileStatsList) {
			// System.out.println(fileStats);
			totalTokens += fileStats.getTotalTokens();
			avgUniqueTokens += fileStats.getUniqueTokens();

			for (Entry<String, Integer> tokenEntry : fileStats.getTokenMap()
					.entrySet()) {

				String token = tokenEntry.getKey();
				Integer count = tokenEntry.getValue();
				Integer totCount;

				if ((totCount = totalTokenMap.get(token)) == null) {
					totalTokenMap.put(token.trim(), count);
				} else {
					totalTokenMap.put(token.trim(), totCount + count);
				}
			}
		}

		avgUniqueTokens = avgUniqueTokens / fileStatsList.size();
		tokenStats.setTotalTokens(totalTokens);
		tokenStats.setUniqueTokens(totalTokenMap.size());
		tokenStats.setAvgUniqueTokens(avgUniqueTokens);
		// System.out.println(tokenStats);
		printStats();

	}

	/**
	 * This function start the tokenizing process by creating specified no of
	 * threads. for tokening the file. No of threads
	 * 
	 * @param noOfThreads
	 * @return 0 on success
	 */
	public int startProcess(int noOfThreads) {

		ExecutorService executorService = Executors
				.newFixedThreadPool(noOfThreads);
		File files[] = getFileList();
		
		//No change in indexing
		if(files.length==fileNametoIndexMap.size()){
			skipIndexing=true;
			return 0;
		}
		
		
		for (File file : files) {
			
			if(fileNametoIndexMap.get(file.getName())!=null){
				continue;
			}
			else{
				//New file to index
				++fileCounter;
				fileNametoIndexMap.put(file.getName(), fileCounter);
				fileIndexMap.put(fileCounter,file);
				
			}

			if (file.isFile()) {

				FileStats fileStats = new FileStats(file);
				FileReaderThread fileReaderThread = new FileReaderThread(
						fileStats);
				executorService.execute(fileReaderThread);
				// System.out.println(file.getName());
				fileStatsList.add(fileStats);

			}
		}
		try {
			executorService.shutdown();
			// System.out.println(executorService.isTerminated());
			executorService.awaitTermination(30, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(executorService.isTerminated());
		// System.out.println("Finished all Jobs");

		/*
		 * for (FileStats fileStats : fileStatsList) {
		 * System.out.println(fileStats); }
		 */
		// System.out.println(fileStatsList.size());
		// computeTotalStats();
		IndexFileManager.writeObjectFile(idx1, fileNametoIndexMap);
		IndexFileManager.writeObjectFile(idx2, fileIndexMap);
		return 0;
	}

	public String getCollectionDirectory() {
		return collectionDirectory;
	}

	public void setCollectionDirectory(String collectionDirectory) {
		this.collectionDirectory = collectionDirectory;
	}

	public ArrayList<FileStats> getFileStatsList() {
		return fileStatsList;
	}

	public void setFileStatsList(ArrayList<FileStats> fileStatsList) {
		this.fileStatsList = fileStatsList;
	}

	public TokenStats getTokenStats() {
		return tokenStats;
	}

	public void setTokenStats(TokenStats tokenStats) {
		this.tokenStats = tokenStats;
	}

	public HashMap<String, Integer> getFileNametoIndexMap() {
		return fileNametoIndexMap;
	}

	public void setFileNametoIndexMap(
			HashMap<String, Integer> fileNametoIndexMap) {
		this.fileNametoIndexMap = fileNametoIndexMap;
	}

	public HashMap<Integer, File> getFileIndexMap() {
		return fileIndexMap;
	}

	public void setFileIndexMap(HashMap<Integer, File> fileIndexMap) {
		this.fileIndexMap = fileIndexMap;
	}

	public int getFileCounter() {
		return fileCounter;
	}

	public void setFileCounter(int fileCounter) {
		this.fileCounter = fileCounter;
	}

	public String getIdx1() {
		return idx1;
	}

	public void setIdx1(String idx1) {
		this.idx1 = idx1;
	}

	public String getIdx2() {
		return idx2;
	}

	public void setIdx2(String idx2) {
		this.idx2 = idx2;
	}

	public boolean isSkipIndexing() {
		return skipIndexing;
	}

	public void setSkipIndexing(boolean skipIndexing) {
		this.skipIndexing = skipIndexing;
	}

}
