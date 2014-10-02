package test;

import cpath2API.SearchResponse;
import cpath2API.TopPathwaysCommand;
import cpath2API.TopPathwaysCommand.DATA_SOURCE;


public class TopPathwayCommandTest {
	static TopPathwaysCommand topPathway = new TopPathwaysCommand();
	
	
	private static void download(DATA_SOURCE dataSource) {
		topPathway.getXMLURL("Homo%20sapiens", dataSource.name());		
		System.out.println("top_pathways XML OK!");
		SearchResponse searchResponse = new SearchResponse(topPathway.getDocument());
		searchResponse.parseXMLDOM();
		System.out.println("tostring():" + searchResponse.toString());
		System.out.println("Parser XML TO " + dataSource.name() + " OK!");
		int n = searchResponse.getSize() - 1;
		System.out.println("Numero pathway: " + n);
		searchResponse.getBioPAXModel(searchResponse.getSize() - 1);
		System.out.println("GENERATE LAST MODEL OK!");
		for (int i = 0; i < n; ++i) {
			searchResponse.getBioPAXModel(i);
			System.out.println("CREATE BIOPAX MODEL " + i + " FROM " + dataSource.name() + " OK!");
		}
	}
	
	public static void main(String[] args) {	
		download(DATA_SOURCE.REACTOME);
		download(DATA_SOURCE.HUMANCYC);
		download(DATA_SOURCE.NCI_NATURE);
		download(DATA_SOURCE.PID);
		
		
		System.out.println("\n\n\nTERMINATED!");
	}
}

