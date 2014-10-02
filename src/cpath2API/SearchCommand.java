package cpath2API;

import java.io.OutputStream;


/**
 * Classe che gestisce il comando SEARCH di cPath2.
 * Questo comando esegue query testuali utilizzando le query Luene
 * 
 * @author Alessandro
 *
 */
public class SearchCommand extends CPathConnection {
	
	/**
	 * Costruttore di default.
	 */
	public SearchCommand() {
		super("search");
	}
	
	/**
	 * Database a cui si ci può connettere.
	 * Sono i possibili valiri per il campo "datasouce" del comando SEARCH.
	 */
	public enum DATA_SOURCE {
		REACTOME("reactome"),
		PID("pid"),
		PHOSPHOSITE("phosphosite"),	// ERRORE SU getReactionPathways()
		HUMANCYC("humancyc"),	//<<<--------- DA ERRORE
		HPRD("HPRD"),
		PANTHER("panther");
		
		private String datasuorce;
		
		private DATA_SOURCE(String datasource) {
			this.datasuorce = datasource;
		}

		/**
		 * @return the kind
		 */
		protected String getDatasource() {
			return datasuorce;
		}				
	}
	
	/**
	 * Questo metodo effettua una ricerca su tutti i modelli BioPAX di un'entità biologica. 
	 * 
	 * @param entityName - nome dell'entità
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getBasicTextSearch(String entityName, OutputStream outputStream) {
		getSource(this.command + ".xml?q=" + entityName, outputStream);
	}

	/**
	 * Questo metodo effettua una ricerca sui modelli BioPAX di tipo Xref di un'entità biologica. 
	 * 
	 * @param entityName - nome dell'entità
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */	
	public void getBasicXrefId(String entityName, OutputStream outputStream) {
		getSource(this.command + "?q=xrefid:" + entityName, outputStream);
	}
	
	/**
	 * Questo comando ritorna una schema XML con i riferimenti a proteina di un'antità.
	 * 
	 * @param entityName - nome dell'entità
	 * @param dataSource - nome del database
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getHumanProteinRef(String entityName, DATA_SOURCE dataSource, OutputStream outputStream) {
		getSource(this.command + "?q=" + entityName + "&type=proteinreference&organism=homo20%sapiencens&datasource=" 
						+ dataSource, 
				  outputStream);
	}
	
	/**
	 * Questo metodo ritorna una schema XML con i riferimenti a proteina di un'antità dell'essere umano (specificato con la notazione di NCBI).  
	 * 
	 * @param entityName - nome dell'entità 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato 
	 */
	public void getSimilarHumanProteinRef(String entityName, OutputStream outputStream) {
		getSource(this.command + ".xml?q=name:'" + entityName + "'&type=proteinreference&organism=9606", 
				  outputStream);
	}
	
	/**
	 * Questo metodo ritorna uno schema XML con le wildcard delle interazioni umane controllare.
	 * 
	 * @param organsm - nome dell'organismo
	 * @param dataSource - nome del database
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getWildcardControlHumanInteraction(String organsm, 
												  DATA_SOURCE dataSource, 
												  OutputStream outputStream) {
		getSource(this.command + "?q=brc*&type=control&organism=" + organsm 
						+ "&datasource=" + dataSource, 
				  outputStream);
	}
	
	/**
	 * Questo metodo ritorno una sche XML dove ci sono i dati della quarta pagina inizianti per a.
	 * 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getForthPageStarterA(OutputStream outputStream) {
		getSource(this.command + "?q=a*&page=3", outputStream);
	}
	
	/**
	 * Questo metodo ritorna una schema XML con tutte la interazioni con la parola Brinding che ha Pathway Commons.
	 * 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getControlIntractionContainWordBinding(OutputStream outputStream) {
		getSource(this.command + "?q=+binding%20NOT%20transcription*&type=control&page=0", outputStream); 
	}
	
	/**
	 * Questo metodo ritorna una schema XML con tutte la interazioni con la parola Immune che ha Pathway Commons. 
	 * 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getAllInteractionWordImmune(OutputStream outputStream) {
		getSource(this.command + "?q=pathway:immune*&type=conversion", outputStream); 
	}
	
	/**
	 * Questo metodo ritorna una schema XML con tutte la interazioni che ha un determinato database.
	 *  
	 * @param dataSource - nome del database
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getReactionPathways(DATA_SOURCE dataSource, OutputStream outputStream) {
		getSource(this.command + "?q=*&type=pathway&datasource=" + dataSource.name(), outputStream);
	}
	
	/**
	 * Questo metodo ritorna uno schema XML con la lista degli organismi. 
	 * 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getListOrganisms(OutputStream outputStream) {
		getSource(this.command + "?q=*&type=biosource", outputStream); 
	}
}
