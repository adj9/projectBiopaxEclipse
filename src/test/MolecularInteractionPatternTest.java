package test;

import java.util.List;

import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.pattern.Match;

import analizeBioPAXModel.MolecularInteractionPattern;
import cpath2API.GraphCommand;
import cpath2API.GraphCommand.GENERATE_MODEL_KIND;

public class MolecularInteractionPatternTest {
	public static void main(String[] args) {
		GraphCommand graph = new GraphCommand();
		
		Model kras = graph.getModelOfEntity("KRASP1", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL kras");
		/*Model tp53 = graph.getModelOfEntity("tp53", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL tp53");
		Model dcc = graph.getModelOfEntity("dcc", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL dcc");*/	
		
		MolecularInteractionPattern molecularInteraction = new MolecularInteractionPattern();
		
		List<Match> result = molecularInteraction.executePattern(kras, "KRASP1");/*
		molecularInteraction.writeiSIFResultPattern(result, "kras");*/
		System.out.println("TERMINATE");
	}
}
