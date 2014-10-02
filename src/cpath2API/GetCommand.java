package cpath2API;

import java.io.OutputStream;


/**
 * Classe che gestisce il comando GET di cPath2.
 * Questo comando consente di recuperare tutto le intità, interazioni di una pathway. 
 * 
 * @author Alessandro
 *
 */
public class GetCommand extends CPathConnection {

	/**
	 * Costruttore di default.
	 */	
	public GetCommand() {
		super("get");

	}
	
	/**
	 * Questo metodo consente di recuperare un documenti BioPAX tramite il nome dell'entità (fa uso dell'ID BioPAX).
	 * 
	 * @param entityName - nome dell'entità
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getBioPAXRappresentationOfIdentifiers(String entityName, OutputStream outputStream) {
		getSource(this.command + "?uri=http://identifiers.org/uniprot/" + entityName, outputStream);
	}
	
	/**
	 * Questo metodo consente di recuperare i riferimenti Xref di geni.
	 * 
	 * @param entityName - nome dell'entità
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void getXrefsBioPAXByGene(String entityName, OutputStream outputStream) {
		getSource(this.command + "?uri=" + entityName, outputStream);
	}
}
 