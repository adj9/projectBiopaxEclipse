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
import analizeBioPAXModel.ControlsExpressionWithTemplateReacPattern;
import analizeBioPAXModel.ExecutePattern;
import analizeBioPAXModel.InComplexWithPattern;
import analizeBioPAXModel.MolecularInteractionPattern;


public class AnalizeModelTest2 {
	public static void analizeModel(Model model, String lable, ExecutePattern pattern, SIFEnum sifEnum) {				
		List<Match> patternMatch = pattern.executePattern(model, lable);
		System.out.println("OK " + lable + " inComplexWith");	
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(
					new File(System.getProperty("user.dir") + "/" 
									+ lable + '_' + sifEnum.getTag() + ".sif"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			pattern.writeResultAsSIF(out, lable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("CREATE SIF FILE OF " + lable);
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

		InComplexWithPattern inComplexWith = new InComplexWithPattern();
		MolecularInteractionPattern molecularInteraction = new MolecularInteractionPattern();
		ControlsExpressionWithTemplateReacPattern controlsExpressionWithTemplateReact = 
				new ControlsExpressionWithTemplateReacPattern();
		
//		Faccio pattern	
		System.out.println("KRAS");
		analizeModel(kras, "KRAS", inComplexWith, SIFEnum.IN_COMPLEX_WITH);
		
		System.out.println("DCC");
		analizeModel(dcc, "DCC", inComplexWith, SIFEnum.IN_COMPLEX_WITH);
		
		/*System.out.println("TP53");
		analizeModel(kras, "TP53", inComplexWith, SIFEnum.IN_COMPLEX_WITH);*/
		
		System.out.println("KRAS");
		analizeModel(kras, "KRAS", molecularInteraction, SIFEnum.INTERACTS_WITH);
		
		System.out.println("DCC");
		analizeModel(dcc, "KRAS", molecularInteraction, SIFEnum.INTERACTS_WITH);
		
		System.out.println("TP53");
		analizeModel(kras, "KRAS", molecularInteraction, SIFEnum.INTERACTS_WITH);
		
		System.out.println("KRAS");
		analizeModel(kras, "KRAS", controlsExpressionWithTemplateReact, SIFEnum.CONTROLS_EXPRESSION_OF);
		
		System.out.println("DCC");
		analizeModel(dcc, "DCC", controlsExpressionWithTemplateReact, SIFEnum.CONTROLS_EXPRESSION_OF);
		
		System.out.println("TP53");
		analizeModel(tp53, "TP53", controlsExpressionWithTemplateReact, SIFEnum.CONTROLS_EXPRESSION_OF);
		/*
		analizeModelInComplexWith(kras, "KRAS");
		
		System.out.println("DCC");
		analizeModelInComplexWith(dcc, "DCC");
		
		System.out.println("TP53");
		analizeModelInComplexWith(tp53, "TP53");
//		End Of This Pattern
		
//		Faccio pattern	-	molecularInteraction
		System.out.println("KRAS");
		analizeModelMolecularInteraction(kras, "KRAS");
		
		System.out.println("DCC");
		analizeModelInComplexWith(dcc, "DCC");
		
		System.out.println("TP53");
		analizeModelMolecularInteraction(tp53, "TP53");
//		End Of This Pattern		

//		Faccio pattern	-	ControlsExpressionWithTemplateReacPattern
		System.out.println("KRAS");
		analizeControlsExpressionWithTemplateReacPattern(kras, "KRAS");
		
		System.out.println("DCC");
		analizeControlsExpressionWithTemplateReacPattern(dcc, "DCC");
		
		System.out.println("TP53");
		analizeControlsExpressionWithTemplateReacPattern(tp53, "TP53");*/
//		End Of This Pattern		
		
		
		System.out.println("\n\n\nTERMINATE!");
	}
}
