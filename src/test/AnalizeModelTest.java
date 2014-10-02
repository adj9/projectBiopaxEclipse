package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.pattern.Match;
import org.biopax.paxtools.pattern.miner.SIFEnum;

import cpath2API.GraphCommand;
import cpath2API.GraphCommand.GENERATE_MODEL_KIND;
import analizeBioPAXModel.ControlsExpressionWithConversionPattern;
import analizeBioPAXModel.ControlsExpressionWithTemplateReacPattern;
import analizeBioPAXModel.InComplexWithPattern;
import analizeBioPAXModel.MolecularInteractionPattern;


public class AnalizeModelTest {
	public static void analizeModelInComplexWith(Model model, String entityName) {				
		InComplexWithPattern inComplexWith = new InComplexWithPattern();
		
		List<Match> patternMatch = inComplexWith.executePattern(model, entityName);
		System.out.println("OK " + entityName + " " + SIFEnum.IN_COMPLEX_WITH.getTag());	
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/" 
									+ entityName + '_' + SIFEnum.IN_COMPLEX_WITH.getTag() + ".sif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			inComplexWith.writeResultAsSIF(out, entityName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("CREATE SIF FILE OF " + entityName);
	} 
	
	public static void analizeModelMolecularInteraction(Model model, String entityName) {				
		MolecularInteractionPattern molecularInteraction = new MolecularInteractionPattern();
		
		List<Match> patternMatch = molecularInteraction.executePattern(model, entityName);
		System.out.println("OK " + entityName + " " + SIFEnum.INTERACTS_WITH.getTag());	
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/" 
									+ entityName + '_' + SIFEnum.INTERACTS_WITH.getTag() + ".sif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			molecularInteraction.writeResultAsSIF(out, entityName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("CREATE SIF FILE OF " + entityName);
	} 
	
	public static void analizaModelControlsExpressionWithConversion(Model model, String entityName) {
		ControlsExpressionWithTemplateReacPattern controlsExpressionWithTemplateReac = new ControlsExpressionWithTemplateReacPattern();
		List<Match> patternMatch = controlsExpressionWithTemplateReac.executePattern(model, entityName);
		System.out.println("OK " + entityName + " " + SIFEnum.INTERACTS_WITH.getTag());	
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/" 
									+ entityName + '_' + SIFEnum.INTERACTS_WITH.getTag() + ".sif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			controlsExpressionWithTemplateReac.writeResultAsSIF(out, entityName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("CREATE SIF FILE OF " + entityName);
	}
	
	public static void analizaModelControlsExpressionWithTemplateReact(Model model, String entityName) {				
		ControlsExpressionWithTemplateReacPattern controlsExpressionWithTemplateReact = 
				new ControlsExpressionWithTemplateReacPattern();
		
		List<Match> patternMatch = controlsExpressionWithTemplateReact.executePattern(model, entityName);
		System.out.println("OK " + entityName + " " + SIFEnum.CONTROLS_EXPRESSION_OF.getTag());	
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/" 
									+ entityName + '_' + SIFEnum.CONTROLS_EXPRESSION_OF.getTag() + ".sif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			controlsExpressionWithTemplateReact.writeResultAsSIF(out, entityName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("CREATE SIF FILE OF " + entityName);
	}
		
	public static void main(String[] args) {
//		Genero modelli
		GraphCommand graph = new GraphCommand();
		
		Model kras = graph.getModelOfEntity("KRAS", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL KRAS");
		Model dcc = graph.getModelOfEntity("DCC", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL DCC");
		Model tp53 = graph.getModelOfEntity("TP53", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL TP53");		
		/*Model m = graph.getModelOfEntity("kras", GENERATE_MODEL_KIND.COMMONSTREAM);
		System.out.println("ALTRO OK");*/		
//		---		

//		Faccio pattern	
		System.out.println("KRAS");
		analizeModelInComplexWith(kras, "KRAS");

		System.out.println("DCC");
		analizeModelInComplexWith(dcc, "DCC");
		
		System.out.println("TP53");
		analizeModelInComplexWith(tp53, "TP53");
//		End Of This Pattern		
		
//		Faccio pattern	
		System.out.println("KRAS");
		analizeModelInComplexWith(kras, "KRAS");
		
		System.out.println("DCC");
		analizeModelInComplexWith(dcc, "DCC");
		
		System.out.println("TP53");
		analizeModelInComplexWith(tp53, "TP53");
//		End Of This Pattern				
		
//		Faccio pattern	
		System.out.println("KRAS");
		analizaModelControlsExpressionWithTemplateReact(kras, "KRAS");
		
		System.out.println("DCC");
		analizaModelControlsExpressionWithTemplateReact(dcc, "DCC");
		
		System.out.println("TP53");
		analizaModelControlsExpressionWithTemplateReact(tp53, "TP53");
//		End Of This Pattern				

//		Faccio pattern 
		System.out.println("KRAS");
		analizaModelControlsExpressionWithConversion(kras, "KRAS");
		
		System.out.println("DCC");
		analizaModelControlsExpressionWithConversion(dcc, "DCC");
		
		System.out.println("TP53");
		analizaModelControlsExpressionWithConversion(tp53, "TP53");
//		End Of Pattern		
	
		
		
		System.out.println("\n\n\nTERMINATE!");
	}
}
