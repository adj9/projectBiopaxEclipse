package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.biopax.paxtools.model.Model;

import cpath2API.GetCommand;
import cpath2API.GraphCommand;
import cpath2API.GraphCommand.GENERATE_MODEL_KIND;
import cpath2API.IDMappingCommand;
import cpath2API.SearchCommand;
import cpath2API.SearchCommand.DATA_SOURCE;
import cpath2API.TraverseCommand;

public class CPath2APITest {
	static IDMappingCommand id = new IDMappingCommand();
	
	private static void search(FileOutputStream outFile) {
		SearchCommand search = new SearchCommand();

		search.getBasicTextSearch("Q06609", outFile);			
		System.out.println("BASIC TEXT SEACH OK!");
		search.getBasicXrefId("Q06609", outFile);	
		System.out.println("XREFID OK!");
		search.getHumanProteinRef("tp53", SearchCommand.DATA_SOURCE.HPRD, outFile);
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.HPRD + " OK!");

		/*ERROREsearch.getHumanProteinRef(SearchCommand.DATA_SOURCE.HUMANCYC, outFile);//ERRORE
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.HUMANCYC + " OK!");*/

		search.getHumanProteinRef("brca2", SearchCommand.DATA_SOURCE.PANTHER, outFile);		
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.PANTHER + " brca2 OK!");
		search.getHumanProteinRef("kras", SearchCommand.DATA_SOURCE.PHOSPHOSITE, outFile);
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.PHOSPHOSITE + " kras OK!");
		search.getHumanProteinRef("dcc", SearchCommand.DATA_SOURCE.PHOSPHOSITE, outFile);
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.PHOSPHOSITE + " dcc OK!");
		search.getHumanProteinRef("tp53", SearchCommand.DATA_SOURCE.PID, outFile);
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.PID + " tp53 OK!");
		search.getHumanProteinRef("tp53", SearchCommand.DATA_SOURCE.REACTOME, outFile);
		System.out.println("HOMO SAPIENCES TO " + SearchCommand.DATA_SOURCE.REACTOME + " tp53 OK!");

		search.getSimilarHumanProteinRef("kras", outFile);
		System.out.println("SIMILAR HOMO SAPIENCE kras OK!");
		search.getWildcardControlHumanInteraction("9606", SearchCommand.DATA_SOURCE.PID, outFile);
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " 
								+ SearchCommand.DATA_SOURCE.PID + " OK!");
		search.getWildcardControlHumanInteraction("9606", SearchCommand.DATA_SOURCE.REACTOME, outFile);
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " 
								+ SearchCommand.DATA_SOURCE.REACTOME + " OK!");
		/*search.getWildcardControlHumanIteraction("9606", DATA_SOURCE.HPRD, outFile);//	NON FUNZIONA 
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " + SearchCommand.DATA_SOURCE.HPRD + " OK!");*/
	
		search.getWildcardControlHumanInteraction("9606", DATA_SOURCE.HUMANCYC, outFile);
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " 
								+ SearchCommand.DATA_SOURCE.HUMANCYC + " OK!");
		search.getWildcardControlHumanInteraction("9606", DATA_SOURCE.PANTHER, outFile);
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " 
								+ SearchCommand.DATA_SOURCE.PANTHER + " OK!");
		search.getWildcardControlHumanInteraction("9606", DATA_SOURCE.PHOSPHOSITE, outFile);
		System.out.println("WILDCARD CONTROL HUMAN INTERACTION " 
								+ SearchCommand.DATA_SOURCE.PHOSPHOSITE + " OK!");

		search.getForthPageStarterA(outFile);
		System.out.println("FORTH PATH STARTER A OK!");
		search.getControlIntractionContainWordBinding(outFile);
		System.out.println("CONTROL INTERACTION WORD BINDING OK!");
		search.getAllInteractionWordImmune(outFile);
		System.out.println("ALL INTERACTION WITH WORD IMMUE OK!");
	
		search.getReactionPathways(SearchCommand.DATA_SOURCE.HUMANCYC, outFile);
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.HUMANCYC + " OK!");
		search.getReactionPathways(SearchCommand.DATA_SOURCE.PANTHER, outFile);
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.PANTHER + " OK!");
		//search.getReactionPathways(SearchCommand.DATA_SOURCE.PHOSPHOSITE);	NON FUNZIONA
		//System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.PHOSPHOSITE + " OK!");
		search.getReactionPathways(SearchCommand.DATA_SOURCE.PID, outFile);
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.PID + " OK!");
		search.getReactionPathways(SearchCommand.DATA_SOURCE.REACTOME, outFile);
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.REACTOME + " OK!");
		search.getReactionPathways(SearchCommand.DATA_SOURCE.REACTOME, outFile);
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.REACTOME + " OK!");
		/*search.getReactionPathways(SearchCommand.DATA_SOURCE.HPRD, outFile);	//NON FUNZIONA
		System.out.println("REACTIONN PATHWAYS " + SearchCommand.DATA_SOURCE.HPRD + " OK!");*/
		search.getListOrganisms(outFile);
		System.out.println("LIST ORGANISMS OK!");
	}
	
	private static void get(FileOutputStream outFile) {
		GetCommand get = new GetCommand();
		
		get.getBioPAXRappresentationOfIdentifiers(id.getIDOf("Q06609"), outFile);
		System.out.println("BIOPAX RAPPRESENTATION OF IDENTIFIERS.ORG OK!");
		get.getXrefsBioPAXByGene("COL5A1", outFile);
		System.out.println("XREFS BIOPAX BY GENE");
	}
	
	private static void graph(FileOutputStream outFile) {
		GraphCommand graph = new GraphCommand();
		
		graph.getNearestSIF("KRAS", 
						    GraphCommand.KIND.COMMONSTREAM, 
						    GraphCommand.FORMAT.BINARY_SIF, 
						    outFile);
		System.out.println("NEIGHBORHOOD SIF " + GraphCommand.KIND.COMMONSTREAM 
								+ " " + GraphCommand.FORMAT.BINARY_SIF + " OK!");
		graph.getNearestSIF("DCC", 
				            GraphCommand.KIND.NEIGHBORHOOD, 
				            GraphCommand.FORMAT.BIOPAX, 
				            outFile);
		System.out.println("NEIGHBORHOOD SIF " + GraphCommand.KIND.NEIGHBORHOOD 
								+ " " + GraphCommand.FORMAT.BIOPAX + " OK!");
		graph.getNearestSIF(id.getIDOf("KRAS"), 
							GraphCommand.KIND.NEIGHBORHOOD, 
							GraphCommand.FORMAT.EXTENDED_BINARY_SIF, 
							outFile);	
		System.out.println("NEIGHBORHOOD SIF " + GraphCommand.KIND.NEIGHBORHOOD 
								+ " " + GraphCommand.FORMAT.EXTENDED_BINARY_SIF + " OK!");

		graph.getUnitaryDistanceNeighborhood("KRAS", GraphCommand.KIND.COMMONSTREAM, outFile);
		System.out.println("UNITARY DISTANCE NEIGHBORHOOD " + GraphCommand.KIND.COMMONSTREAM + " KRAS OK!");
		graph.getUnitaryDistanceNeighborhood("DCC", GraphCommand.KIND.NEIGHBORHOOD, outFile);
		System.out.println("UNITARY DISTANCE NEIGHBORHOOD " + GraphCommand.KIND.NEIGHBORHOOD + " DCC OK!");
		

		graph.getSimilarGeneSymbol("COL5A1", outFile);
		System.out.println("SIMILAR GENE BY SYMBOL COL5A1 " + GraphCommand.KIND.COMMONSTREAM + " OK!");
		
		
		Model kras = graph.getModelOfEntity("KRAS", GENERATE_MODEL_KIND.NEIGHBORHOOD);		
		System.out.println("GENERATE MODEL KRAS WITH " + GENERATE_MODEL_KIND.NEIGHBORHOOD);
		Model dcc = graph.getModelOfEntity("DCC", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL DCC WITH " + GENERATE_MODEL_KIND.NEIGHBORHOOD);
		Model tp53 = graph.getModelOfEntity("TP53", GENERATE_MODEL_KIND.NEIGHBORHOOD);
		System.out.println("GENERATE MODEL TP53 WITH " + GENERATE_MODEL_KIND.NEIGHBORHOOD);		
		Model m = graph.getModelOfEntity("KRAS", GENERATE_MODEL_KIND.COMMONSTREAM);
		System.out.println("GENERATE MODEL KRAS WITH " + GENERATE_MODEL_KIND.COMMONSTREAM);		

	}
	
	private static void traverse(FileOutputStream outFile) {
		TraverseCommand traverse = new TraverseCommand();
		/*
		traverse.getDisplayNameOfOrgamismOfProteinRef("KRAS", outFile);
		System.out.println("DISPLAY NAME OF ORGANISM OF PROTEIN REFERENCE OK!");
		traverse.getURLOrganismProteinRef("DCC", outFile);
		System.out.println("URL ORGANISM PROTEIN REFERENCE OK!");
		traverse.getNamesAllStatesProtein("RAD51", outFile);
		System.out.println("NAMES ALL STATES OF RAD51 PROTEON OK!");
		traverse.getURIsStatesOfHUMAN("BRCA1", outFile);
		System.out.println("URIS STATES OF BRCA1_HUMAN OK!");
		traverse.getSeveralDifferennceObject("BRCA1", outFile);
		System.out.println("SEVERAL DIFFERENT OBJECT OK!");*/
		
		traverse.get("BRCA1", "ProteinReference/organism/displayName", outFile);
		System.out.println("GENERATE XPATHLINK: ProteinReference/organism/displayName ON BRCA1 OK!");
		traverse.get("BRCA1", "ProteinReference/entityReferenceOf:Protein/name", outFile);
		System.out.println("GENERATE XPATHLINK: ProteinReference/entityReferenceOf:Protein/name ON BRCA1 OK!");
	}
	
	private static void idMapping() {
		String code = id.getIDOf("KRAS");
		String site =id.getUniProtUriOf("KRAS"); 
		System.out.println("THE CODE OF KRAS IS " + code);
		System.out.println("UNIPROT SITE OF KRAS IS " + site);
		
		code = id.getIDOf("DCC");		
		site =id.getUniProtUriOf("DCC"); 
		System.out.println("THE CODE OF DCC IS " + code);
		System.out.println("UNIPROT SITE OF DCC IS " + site);
		
		code = id.getIDOf("TP53");	
		site =id.getUniProtUriOf("TP53"); 
		System.out.println("THE CODE OF TP53 IS " + code);
		System.out.println("UNIPROT SITE OF TP53 IS " + site);		
	}
	
	public static void main(String[] args) {
		FileOutputStream outFile = null;
		try {
			outFile = new FileOutputStream(new File(System.getProperty("user.dir") + "/res.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		***TEST CLASSE SearchCommand***
		
		search(outFile);
		
//		***FINE***		

//		***TEST CLASSE GetCommand***
		
		get(outFile);
	
//		***FINE***
		
//		***TEST CLASSE GraphCommand***		
		
		graph(outFile);
				
//		***FINE***
		
		
//		***TEST CLASSE TraverseCommand***
		
		traverse(outFile);
		
//		***FINE***		
		
//		***TEST CLASSE IDMappingCommand***
		
		idMapping();
		
		
		
		
		System.out.println("\n\n\nTERMINATE!");		
	}
}
