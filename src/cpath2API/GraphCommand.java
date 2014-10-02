package cpath2API;

import java.io.OutputStream;

import org.biopax.paxtools.model.Model;


/**
 * Classe che gestisce il comando GRAPH di cPath2.
 * Questo comando esegue aricolari interrogazioni di un grafo che rappresenta una entià biologica.
 * 
 * @author Alessandro
 *
 */
public class GraphCommand extends CPathConnection {
	
	/**
	 * Formati possibili per scaricare i dati. 
	 * Sono i valori del il campo "format".
	 */
	public enum FORMAT {
		BINARY_SIF("BINARY_SIF"),
		BIOPAX("BIOPAX"),
		EXTENDED_BINARY_SIF("EXTENDED_BINARY_SIF"),
		GSEA("GSEA"),
		SBGN("SBGN");
		
		private String format;
      	
      	/**
      	 * Costruttore dell'enumratore.
      	 * 
      	 * @param fomat
      	 */
      	private FORMAT(String format) {
      		this.format = format;
		}

		/**
		 * @return the format
		 */
		protected String getFormat() {
			return format;
		}		      	  
	}
	
	/**
	 * Tip di query possibii del campo "kind" per generare istanze di modelli BioPAX.
	 */
	public enum GENERATE_MODEL_KIND {
		COMMONSTREAM("commonstream"),
		NEIGHBORHOOD("neighborhood");
		
		private String kind;
		
		GENERATE_MODEL_KIND(String kind) {
			this.kind = kind;
		}

		/**
		 * @return the kind
		 */
		protected String getKind() {
			return kind;
		}				
	}
	
	/**
	 * Tip di query possibii del campo "kind".
	 */	
	public enum KIND {
		COMMONSTREAM("commonstream"),
		NEIGHBORHOOD("neighborhood"),
		PATHSBEWEEN("pathsbetween"),	//ERRORE PER getUnitaryDistanceNeighborhood
		PATHSFROMTO("pathsfromoto");
		
		private String kind;
		
		KIND(String kind) {
			this.kind = kind;
		}

		/**
		 * @return the kind
		 */
		protected String getKind() {
			return kind;
		}				
	}
	
	/**
	 * Costruttore di default.
	 */
	public GraphCommand() {
		super("graph");
	}
	
	/**
	 * Questo metodo consente di realizzare la rappresentazioe SIF di un'etità biologica.
	 * 
	 * @param entityName - nome dell'entità
	 * @param commonstream - tipo di interrogazione sul grafo
	 * @param binarySif - tipo di formato 
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getNearestSIF(String entityName, 
							  KIND commonstream, 
							  FORMAT binarySif, 
							  OutputStream outputStream) {		
		getSource(this.command + "?source=" + new IDMappingCommand().getUniProtUriOf(entityName)
				+ "&kind=" + commonstream 
				+ "&format=" + binarySif, 
			  outputStream);	
	}
	
	/**
	 * Questo metodo consente di creare un documento BioPAX di un'entità biologica (contrassegnata dall'ID UniProt).
	 * 
	 * @param entityName - nome dell'entità
	 * @param kind - tipo di interrogazione sul grafo
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getUnitaryDistanceNeighborhood(String entityName, KIND kind, OutputStream outputStream) {
		getSource(this.command + "?source=" + new IDMappingCommand().getIDOf(entityName) + "&kind=" + kind, outputStream);
	}
	
	public void getSimilarGeneSymbol(String entityName, OutputStream outputStream) {
		getSource(this.command + "?source=" + entityName + "&kind=commonstream", outputStream);
	}

	/**
	 * Metodo che ritorno il modello BioPAX di un'entità.
	 * 
	 * @param entityName - nome dell'entità
	 * @param kind - tipo di query
	 * @return istanza di tipo Model
	 */
	public Model getModelOfEntity(String entityName, GENERATE_MODEL_KIND kind) {
		return generateBioPAXModel(this.command + "?source=" 
										+ entityName + "&kind=" + kind + "&format=BIOPAX");
	}
}
