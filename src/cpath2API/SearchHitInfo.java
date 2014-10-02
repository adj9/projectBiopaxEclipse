package cpath2API;

/**
 * Classe containter per salvare le informazioni di una pathway, le quali sono: 
 * 	1. URI da dove scaricare la pathway BioPAX;
 * 	2. Nome della pathway;
 * 	3. Database da dove è stata scaricata; 
 * 	4. organirmo della pathway
 * 
 * @author Alessandro
 *
 */
public class SearchHitInfo {
	private String pathwayUri;
	private String biopaxClass;
	private String namePathway;
	private String dataSource;
	private String organism;
	
	/**
	 * Costruttore.
	 * 
	 * @param pathwayUri
	 * @param biopaxClass
	 * @param namePathway
	 * @param dataSource
	 * @param organism
	 */
	public SearchHitInfo(String pathwayUri, String biopaxClass, 
						 String namePathway, String dataSource,
						 String organism) {
		this.pathwayUri = pathwayUri;
		this.biopaxClass = biopaxClass;
		this.namePathway = namePathway;
		this.dataSource = dataSource;
		this.organism = organism;
	}

	/**
	 * @return the pathwayUri
	 */
	public String getPathwayUri() {
		return pathwayUri;
	}

	/**
	 * @return the biopaxClass
	 */
	public String getBiopaxClass() {
		return biopaxClass;
	}

	/**
	 * @return the namePathway
	 */
	public String getNamePathway() {
		return namePathway;
	}

	/**
	 * @return the dataSource
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * @return the organism
	 */
	public String getOrganism() {
		return organism;
	}
	
	/**
	 * Metodo toString
	 * 
	 * @return the result stringha costruita medianto l'uso dell'interatore.
	 */
	public String toString() {
		return '[' + pathwayUri + ", " + biopaxClass + ", " + namePathway + ", " + dataSource + ", " + organism + ']';
	}
}
