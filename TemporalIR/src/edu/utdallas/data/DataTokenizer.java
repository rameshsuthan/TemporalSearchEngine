package edu.utdallas.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.time.TimeAnnotations;
import edu.stanford.nlp.time.TimeAnnotator;
import edu.stanford.nlp.time.TimeExpression;
import edu.stanford.nlp.util.CoreMap;
import edu.utdallas.fileio.FileStats;
import edu.utdallas.fileio.FileUtil;
import edu.utdallas.fileio.StopWords;

/**
 * This Tokenizer Class is used to parse the XML files to get the tokens
 * 
 * @author ramesh
 *
 */

public class DataTokenizer {
	private StringBuilder fileData = new StringBuilder();
	private File file;
	private FileStats fileStats;
	private static AnnotationPipeline pipeline;

	public DataTokenizer(File file, FileStats fileStats) {
		this.file = file;
		this.fileStats = fileStats;
		if(pipeline==null){
			pipeline=new AnnotationPipeline();
			Properties props = new Properties();
			pipeline.addAnnotator(new TokenizerAnnotator(false));
			pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
			pipeline.addAnnotator(new POSTaggerAnnotator(false));
			pipeline.addAnnotator(new TimeAnnotator("sutime", props));
		}
	}
	
	public  int readFile(String file) {
		if (FileUtil.isFilePresent(file)) {
			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					line=line.trim();
					if(!line.isEmpty()){
						fileData.append(line);
					}

				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 0;

		} else {
			return -1;
		}

	}

	
	
	
	

	/**
	 * This function generates tokenizes the document 
	 */
	public void tokenize() {

		HashMap<String, Integer> tokenMap = fileStats.getTokenMap();
		long totalTokens = 0;
		
		//Replace the '.' at the end of the line with the empty space
		String fileStr=fileData.toString().replaceAll("/. ", " ");
		
		//Split the tokens using space or / or - or \ as delimiter
		String[] tokens = fileStr.split("\\s+|\\\\|\\-|\\/");
		Integer count = null;
		

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
			if ((count = tokenMap.get(token)) == null) {
				tokenMap.put(token.trim(), 1);
			} else {
				tokenMap.put(token.trim(), count + 1);
			}
			totalTokens++;	
		}

		Integer stopWordCount;
		for(String stopword:StopWords.stopWordList){
			//stopword=fixedLengthString(stopword, MaxKeyLen);
			if( (stopWordCount=tokenMap.remove(stopword.toLowerCase()))!=null){
				//System.out.println(stopword+"Removed");
				totalTokens-=stopWordCount;
			}
		}
		fileStats.setUniqueTokens(tokenMap.size());
		fileStats.setTotalTokens(totalTokens);
		System.out.println(tokenMap);

	}
	
	public void annotate(){
		
		for (String text : fileData.toString().split("\\s+")) {
			Annotation annotation = new Annotation(text);
			annotation.set(CoreAnnotations.DocDateAnnotation.class,
					"2013-07-14");
			pipeline.annotate(annotation);
			// System.out.println(annotation.get(CoreAnnotations.TextAnnotation.class));
			List<CoreMap> timexAnnsAll = annotation
					.get(TimeAnnotations.TimexAnnotations.class);
			for (CoreMap cm : timexAnnsAll) {
				List<CoreLabel> tokens = cm
						.get(CoreAnnotations.TokensAnnotation.class);
				/*
				 * System.out.println(cm + " [from char offset " +
				 * tokens.get(0).
				 * get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
				 * " to " + tokens.get(tokens.size() -
				 * 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) +
				 * ']' + " --> " +
				 * cm.get(TimeExpression.Annotation.class).getTemporal());
				 */
				if (cm.get(TimeExpression.Annotation.class).getTemporal()!= null) {
					/*System.out.println("-->"+cm.get(TimeExpression.Annotation.class)
							.getTemporal());*/
					fileStats.getTemporalSet().add(cm.get(TimeExpression.Annotation.class)
							.getTemporal().toString());
				}
			}

			//System.out.println("--");
		}
	}

		public int start() {
			
			readFile(file.getAbsolutePath());
			annotate();
			tokenize();
			
		return 0;

	}

}
