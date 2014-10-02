package analizeBioPAXModel;


// Classi per la gestione dello stream e per il tipo di ritorno del metodo exesecutePattern()
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

// Classi della libreria Paxtools, utilizzate dalle classi della libreria biopax-pattern 
import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.ProteinReference;

// Classi della libreria biopax-pattern
import org.biopax.paxtools.pattern.Match;
import org.biopax.paxtools.pattern.Pattern;
import org.biopax.paxtools.pattern.Searcher;

// Classe del package cpath2API per generare l'URI dell'entità fisica principalr
import cpath2API.IDMappingCommand;


/**
 * Classe che contiene i metodi per eseguire la ricerca di un generico pattern in un modello BioPAX 
 * e stampa il risultato in un file sif.
 * 
 * @author Alessandro
 *
 */
public abstract class ExecutePattern {
	protected Match match;
	protected List<Match> patterResult;	
	protected String relationship;

	private IDMappingCommand idMapping;
	
	/**
	 * Costruttore di classe.
	 * 
	 * @param relationship - relazione che caratterizza il pattern
	 */
	protected ExecutePattern(String relationship) {
		this.match = null;
		this.patterResult = null;
		this.relationship = relationship;
		this.idMapping = new IDMappingCommand();
	}	
	
	/**
	 * Metodo il interfaccia per eseguire un pattern generico sul modello BioPAX.
	 * 
	 * @param model - modello BioPAX
	 * @param entityName - nome dell'entità fisica
	 * @return uno lista di oggetti Match (classe della libreria biopax-pattern)
	 */
	public abstract List<Match> executePattern(Model model, String entityName);
	
	/**
	 * Metodo per eseguire la ricerca in un modello BioPAX tramite un generico pattern.
	 *  
	 * @param model - modello BioPAXX
	 * @param entityName - nome dell'entità fisica
	 * @param pattern - instanza del pattern per eseguire la ricerca 
	 * @param label - label per  settare l'istanza Match (classe della libreria biopax-pattern)
	 * @return una lista di Match 
	 */
	public List<Match> executePattern(Model model, String entityName, Pattern pattern, String label) {
//		Instanziazione dell'istanza ProteinReference a partire dal nome dell'entità		
		ProteinReference proteinRef = 
				(ProteinReference) model.getByID(idMapping.getUniProtUriOf(entityName));
		
//		Instanziazione dell'instanza Match		
		Match match = new Match(pattern.size());
		
//		Fase di pre-set dell'oggetto match con il riferimento alla proteina   	
		match.set(proteinRef, pattern.indexOf(label));
		
//		Ricerca		
		patterResult = Searcher.search(match, pattern);
		
		return patterResult;
	}
	
	/**
	 * Metodo per scrivere il file sif con le relazioni dirette dell'entità.
	 * 
	 * @param out - istanza di output su cui stampare il risultato
	 * @param entityName - nome dell'entità fisica
	 * @throws IOException - eccezione per la scrittura sulla stream
	 */
	public void writeResultAsSIF(OutputStream out, String entityName) throws IOException {				
//		Stream per la stampa del risultato 		
		OutputStreamWriter writer = new OutputStreamWriter(out);

//		Intestazione delle colonne		
		writer.write("ID_BioPAXElement\tRelationship\tID_BioPAXElementFromMatch\n");
		
//		Dichiarazione degli elementi utili per prelevare gli URIs interessati:
//			1.	els - array di elementi BioPAX che mecciano con l'entità;
//			2.	uriEntityName - URI dell'elemento BioPAX non mecciato.
//								E' già inizializzato utilizzando il comando INMAPPING di Pathway Commons
		BioPAXElement[] els = null;
		String uriEntityName = idMapping.getUniProtUriOf(entityName);
//		Ciclo che itera sulla mappa		
		for (int j = 0; j < patterResult.size(); ++j) {			
//			Ciclo che itera su ogni match			
			for (Match match : patterResult) {
				els = match.getVariables();
//				Ciclo che itera sugli elementi BioPAX dell'oggetto match				
				for (int k = 0; k < els.length; ++k) {
//					els[k].getRDFId() è l'URI dell'elemento BioPAX dopo in posizion k dell'array 
//					di un'oggetto Match
					writer.write(uriEntityName + "\t" + relationship + "\t" + els[k].getRDFId() + "\n");									
				}//		--- File 3° ciclo
			}//		--- File 2° ciclo
		}//		--- File 1° ciclo

//		Aggiornamento dello stream		
		writer.flush();
	}
}
