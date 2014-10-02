package cpath2API;

import java.io.OutputStream;


/**
 * Classe che gestisce il comando TRAVERSE di cPath2.
 * Questo comando esegue un'interrogazione su un grafo tramite l'XPath-like che descrive proprietà del path del grafo per poter estrarre una  sotto rete.
 * 
 * @author Alessandro
 *
 */
public class TraverseCommand extends CPathConnection {

	/**
	 * Costruttore di default.
	 */	
	public TraverseCommand() {
		super("traverse");		
	}
	
	/**
	 * Questo metodo esegue un'interrogazione si un grafo rappresentati d un'entità biologica. 
	 * 
	 * @param entityName - nome dell'entità
	 * @param xPathLike - interrogazione XPath-like
	 * @param outputStream - istanza di tipo OutputStream per scrivere il risultato
	 */
	public void get(String entityName, String xPathLike, OutputStream outputStream) {
		getSource(this.command + "?uri=" + new IDMappingCommand().getUniProtUriOf(entityName) 
						+ "&path=" + xPathLike, 
				  outputStream);
	}	
}
