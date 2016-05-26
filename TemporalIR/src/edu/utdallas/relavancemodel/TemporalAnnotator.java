package edu.utdallas.relavancemodel;

import java.util.ArrayList;
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

public class TemporalAnnotator {
	
	private static AnnotationPipeline pipeline;


 public TemporalAnnotator(){
	 if(pipeline==null){
			pipeline=new AnnotationPipeline();
			Properties props = new Properties();
			pipeline.addAnnotator(new TokenizerAnnotator(false));
			pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
			pipeline.addAnnotator(new POSTaggerAnnotator(false));
			pipeline.addAnnotator(new TimeAnnotator("sutime", props));
		}
 }
 
 
 public ArrayList<String> annotate(String data){
		
	 ArrayList<String> temporalAnnList=new ArrayList<String>();
		for (String text : data.split("\\s+")) {
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
					temporalAnnList.add(cm.get(TimeExpression.Annotation.class)
							.getTemporal().toString());
				}
			}

			//System.out.println("--");
		}
		return temporalAnnList;
	}

	
}
