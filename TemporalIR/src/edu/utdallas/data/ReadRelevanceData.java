package edu.utdallas.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.utdallas.relavancemodel.Judgement;
import edu.utdallas.relavancemodel.StoredQuery;

public class ReadRelevanceData {
	private HashMap<String, StoredQuery> storedQMap = new HashMap<String, StoredQuery>();
	private HashMap<String, DataAnnotation> annotationMap = new HashMap<String, DataAnnotation>();
	private HashMap<String,ArrayList<Judgement>> judgementMap= new HashMap<String, ArrayList<Judgement>>();
	private HashMap<String,String> urlMap=new HashMap<String, String>();
	private HashMap<String,Set<String>> relevanceFactorMap=new HashMap<String, Set<String>>();

	public void urlCSV(String fileName) {
		Reader in;
		try {
			in = new FileReader(fileName);

			CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			for (CSVRecord record : parser) {
				String uno = record.get("URLNO");
				String url = record.get("URL");
				urlMap.put(uno, url);
				
			}
			System.out.println(urlMap);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void queryCSV(String fileName) {
		Reader in;
		try {
			in = new FileReader(fileName);

			CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			for (CSVRecord record : parser) {
				String qId = record.get("QID");
				String informationNeed = record.get("IN");
				String query = record.get("QUERY");
				StoredQuery storedQuery = new StoredQuery(qId, informationNeed,
						query);
				storedQMap.put(query, storedQuery);
				System.out.println(storedQuery);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void annotationCSV(String fileName) {
		Reader in;
		try {
			in = new FileReader(fileName);
			CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			for (CSVRecord record : parser) {
				String urlNo = record.get("URLNO").trim();
				String pNo = record.get("PNO").trim();
				String rfn = record.get("RFN").trim();
				String value = record.get("VALUE").trim();

				DataAnnotation annotation;
				if ((annotation = annotationMap.get(pNo.trim())) != null) {
					annotation.getRfnMap().put(rfn, value);
				} else {

					annotation = new DataAnnotation(urlNo, pNo);
					annotation.getRfnMap().put(rfn, value);
					annotationMap.put(pNo, annotation);
				}
				Set<String> docSet;
				
				if((docSet=relevanceFactorMap.get(rfn))==null){
					docSet=new HashSet<String>();
					docSet.add(pNo);
					relevanceFactorMap.put(rfn, docSet);
				}
				else{
					docSet.add(pNo);
				}

			}
			
			System.out.println(annotationMap);
			System.out.println(relevanceFactorMap);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void judgementCSV(String fileName) {
		Reader in;
		try {
			in = new FileReader(fileName);

			CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			for (CSVRecord record : parser) {
				String qId = record.get("QID");
				String urlNo = record.get("URLNO");
				String pNo = record.get("PNO");
				String relv=record.get("REL");
				boolean relevance=false;
				if(relv!=null&&relv.trim().equalsIgnoreCase("positive")){
					relevance=true;
				}
				
				Judgement judgement=new Judgement(qId, urlNo, pNo, relevance);
				ArrayList<Judgement> judgList;
				
				if ((judgList = judgementMap.get(qId.trim())) != null) {
					judgList.add(judgement);
				} else {

					judgList=new ArrayList<Judgement>();
					judgList.add(judgement);
					judgementMap.put(qId, judgList);
				}

				
			}
			System.out.println(judgementMap);


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, StoredQuery> getStoredQMap() {
		return storedQMap;
	}

	public void setStoredQMap(HashMap<String, StoredQuery> storedQMap) {
		this.storedQMap = storedQMap;
	}

	public HashMap<String, DataAnnotation> getAnnotationMap() {
		return annotationMap;
	}

	public void setAnnotationMap(HashMap<String, DataAnnotation> annotationMap) {
		this.annotationMap = annotationMap;
	}

	public HashMap<String, ArrayList<Judgement>> getJudgementMap() {
		return judgementMap;
	}

	public void setJudgementMap(HashMap<String, ArrayList<Judgement>> judgementMap) {
		this.judgementMap = judgementMap;
	}


}
