package edu.utdallas.index;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.utdallas.data.DocStats;
import edu.utdallas.fileio.FileUtil;
import edu.utdallas.lemmatize.LemmaFileStats;

public class InvertedIndexVersion1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int kBlockingVer1 = 8;
	private static int MaxKeyLen = 24;
	private String UnCompressedFileName;
	private String compressedFileName;
	private TreeMap<String, DictionaryValueEntry> dictionary = new TreeMap<String, DictionaryValueEntry>();
	private HashMap<Long, DocStats> docStatMap = new HashMap<Long, DocStats>();
	private HashMap<String,ArrayList<Long>> temporalMap=new HashMap<String, ArrayList<Long>>();

	public String getCompressedFileName() {
		return compressedFileName;
	}

	public void setCompressedFileName(String compressedFileName) {
		this.compressedFileName = compressedFileName;
	}

	// private HashMap<String, DictionaryValueEntry> dictionary;
	public static String fixedLengthString(String string, int length) {
		return String.format("%1$-" + length + "s", string);
	}

	public static InvertedIndexVersion1 loadInvertedIndex(
			String UnCompressedFileName) {
		System.out.println("Loading the Existing invertedIndex");
		InvertedIndexVersion1 invertedIndexVersion1 = null;
		if (FileUtil.isFilePresent(UnCompressedFileName)) {
			invertedIndexVersion1 = (InvertedIndexVersion1) IndexFileManager
					.readIndexFile(UnCompressedFileName);
		}
		return invertedIndexVersion1;
	}
	
	public void updateTemporalMap(LemmaFileStats lemmaFileStats){
		
		for(String temporalVal:lemmaFileStats.getTemporalSet()){
			temporalVal=temporalVal.trim();
			ArrayList<Long> temporalList;
			if( (temporalList=temporalMap.get(temporalVal))==null ){
				temporalList=new ArrayList<Long>();
				temporalList.add(lemmaFileStats.getFileIndex());
				temporalMap.put(temporalVal, temporalList);
			}
			else{
				temporalList.add(lemmaFileStats.getFileIndex());
			}
		}
		
	}

	public void generateInvertedIndexForLemma(
			ArrayList<LemmaFileStats> lemmaFileStatsList) {

		// HashMap<Character[], Integer> test;

		// TreeMap<Character[], DictionaryValueEntry> dictionary1 = new
		// TreeMap<Character[], DictionaryValueEntry>();

		for (LemmaFileStats lemmaFileStats : lemmaFileStatsList) {
			docStatMap.put(lemmaFileStats.getFileIndex(), new DocStats(
					lemmaFileStats.getFileName(), lemmaFileStats.getMax_tf(),
					lemmaFileStats.getDoclen()));
			updateTemporalMap(lemmaFileStats);
			
			//temporalMap.put(lemmaFileStats.getFileIndex(), lemmaFileStats.getTemporalSet());

			for (Entry<String, Integer> lemmaEntrySet : lemmaFileStats
					.getTokenMap().entrySet()) {
				String token = lemmaEntrySet.getKey();
				Integer count = lemmaEntrySet.getValue();
				// token=fixedLengthString(token, MaxKeyLen);

				Integer totCount;

				DictionaryValueEntry dictEntry;

				if ((dictEntry = dictionary.get(token)) == null) {
					dictEntry = new DictionaryValueEntry();
					dictEntry.setDocFreq(1);
					dictEntry.setTermFreq(count);
					dictEntry.getPostingList().add(
							lemmaFileStats.getFileIndex());
					dictEntry.getTermFreqList().add(count);
					dictionary.put(token, dictEntry);

				} else {
					dictEntry.setDocFreq(dictEntry.getDocFreq() + 1);
					dictEntry.setTermFreq(dictEntry.getTermFreq() + count);
					dictEntry.getPostingList().add(
							lemmaFileStats.getFileIndex());
					dictEntry.getTermFreqList().add(count);
				}

			}
		}

		/*
		 * for(String stopword:StopWords.stopWordList){
		 * //stopword=fixedLengthString(stopword, MaxKeyLen);
		 * if(dictionary.remove(stopword.toLowerCase())!=null){
		 * //System.out.println(stopword+"Removed"); } }
		 */

		System.out.println("Number of Inverted List in Version1: "
				+ dictionary.size());

		// IndexFileManager.writeIndexFile(UnCompressedFileName,
		// dictionary,docStatMap);
		IndexFileManager.writeObjectFile(UnCompressedFileName, this);
		System.out.println(temporalMap);

	}

	public static int getkBlockingVer1() {
		return kBlockingVer1;
	}

	public static void setkBlockingVer1(int kBlockingVer1) {
		InvertedIndexVersion1.kBlockingVer1 = kBlockingVer1;
	}

	public static int getMaxKeyLen() {
		return MaxKeyLen;
	}

	public static void setMaxKeyLen(int maxKeyLen) {
		MaxKeyLen = maxKeyLen;
	}

	public String getUnCompressedFileName() {
		return UnCompressedFileName;
	}

	public void setUnCompressedFileName(String unCompressedFileName) {
		UnCompressedFileName = unCompressedFileName;
	}

	public TreeMap<String, DictionaryValueEntry> getDictionary() {
		return dictionary;
	}

	public void setDictionary(TreeMap<String, DictionaryValueEntry> dictionary) {
		this.dictionary = dictionary;
	}

	public HashMap<Long, DocStats> getDocStatMap() {
		return docStatMap;
	}

	public void setDocStatMap(HashMap<Long, DocStats> docStatMap) {
		this.docStatMap = docStatMap;
	}

	public HashMap<String, ArrayList<Long>> getTemporalMap() {
		return temporalMap;
	}

	public void setTemporalMap(HashMap<String, ArrayList<Long>> temporalMap) {
		this.temporalMap = temporalMap;
	}

}
