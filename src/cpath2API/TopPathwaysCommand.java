package cpath2API;


/**
 * Classe che implementa il comando TOP_PATHWAY di cPath2.
 * Reaizza un albero DOM dalla risorsaa XML passando l'organismo ed il database per effettuare la connessione.
 * 
 * @author Alessandro
 *
 */
public class TopPathwaysCommand extends CPathConnection {
	
	/**
	 * Costruttore.
	 */
	public TopPathwaysCommand() {
		super("top_pathways");							// Passo alla classe padre il nume del comando
		
		this.query = null;
	}
	
	/**
	 * Enumeratore di classe per poter decidere il database da dove scaricare la pathway.
	 * 
	 * @author Alessandro
	 *
	 */
	public enum DATA_SOURCE {		
		HUMANCYC("HUMANCYC"),
		NCI_NATURE("NCI_NATURE"),
      	REACTOME("REACTOME"),
      	PID("PID");
      	//UNIPROT("uniprot"),		// Da errore 460
      	//BIOGRID("BIOGRID"),			// Da errore 460
      	//PHOSPHOSITE("PHOSPHOSITE"), // Da errore 460
      	//HPRD("HPRD"),				// Da errore 460
      	//PANTHER("PANTHER");			// Da errore 460
		
      	private String dataSource;
      	
      	/**
      	 * Costruttore dell'enumratore.
      	 * 
      	 * @param dataSource
      	 */
      	DATA_SOURCE(String dataSource) {
      		this.dataSource = dataSource;      		      	
		}

		/**
		 * @return the dataSource
		 */
		protected String getDataSource() {
			return dataSource;
		}      	
		
	}
	/**
	 * Metodo per generare l'URL e generare l'albero DOM dalla risorsa XML per il comando top_pahway 
	 * di Pathway Commons.
	 *  
	 * @param organism
	 * @param dataSource
	 */
	public void getXMLURL(String organism, String dataSource) {
		if (organism == null) {
			organism = "";
		}
		if (dataSource == "") {
			dataSource = "";
		}
		
		if (organism == null && dataSource == null) {
			this.createDOMTree(this.command + '?');
		} else {
			if (organism == null) {
				this.createDOMTree(this.command + ".xml?" + "datasource=" + dataSource);
			} else{
				if (dataSource == null) {
					this.createDOMTree(this.command + ".xml?" + "organism=" + organism);
				} else {
					this.createDOMTree(this.command + ".xml?" + "organism=" + organism + "&datasource=" + dataSource);
				}				
			}			
		}
	}
	
	/**
	 * Metodo per generare l'URL dalla risorsa JSON per il comando top_pahway di Pathway Commons.
	 * 
	 * @param organism
	 * @param dataSource
	 * @return
	 */
	public String getJSONURL(String organism, String dataSource) {
		return URL_BASE_POINT + "/" + command + ".json?" + "organism=" + organism + "&datasource=" + dataSource;
	}	
}
