package cpath2API;

import java.io.IOException;				// Classi per leggere la risorsa OWL
import java.io.InputStream;

import java.net.MalformedURLException;		

import java.net.URL;
import java.net.URLConnection;

import java.util.ArrayList;				// Classi per costruire e naviare il vettore per lainformazine delle pathway

import org.biopax.paxtools.io.BioPAXIOHandler;		// Classi di Paxtools per creare il modello BioPAX
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;

import org.w3c.dom.Document;						// Classi per navigare l'albero DOM
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Classe per salvare le informazioni di delle top pathway, contenute in un file XML.
 * Le informazioni sono:
 * 	1. pathwayUri;
 * 	2. biopaxClass;
 * 	3. namePathway;
 * 	4. dataSource;
 * 	5. organism
 * 
 * @author Alessandro
 *
 */
public class SearchResponse {
	private Document document;	
	
//	Array per contenere le informazioni di una pathway	
	private ArrayList<SearchHitInfo> searchHitsInfo;
	
	private URL url;
	private URLConnection connection;
	
	private BioPAXIOHandler ioHandler;
	
	/**
	 * Costruttore.
	 * 
	 * @param document
	 */
	public SearchResponse(Document document) {
		this.document = document;
		
		this.searchHitsInfo = null;
		
		this.url = null;
		this.connection = null;
		
		this.ioHandler = new SimpleIOHandler();
	}
	
	/**
	 * Metodo per prendere le informazioni dall'abero DOM generato.
	 */
	public void parseXMLDOM() {
//		Prelevo il sotto albero con il tag "searchHit"  		
		NodeList allSearchHit = document.getChildNodes().item(0).getChildNodes();
		Node searchHit = null;
		
//		Creazione del Container		
		searchHitsInfo = new ArrayList<SearchHitInfo>();

//		Lettura delle informazioni del sotto albero		
		for (int i = 0; i < allSearchHit.getLength() - 1; ++i) {
			searchHit = allSearchHit.item(i);

//			Creare un nuovo oggetto SearchHitInfo
			searchHitsInfo.add(new SearchHitInfo(
					searchHit.getChildNodes().item(0).getChildNodes().item(0).getNodeValue(),	// URI della pathway
					searchHit.getChildNodes().item(1).getChildNodes().item(0).getNodeValue(),	// Classe  BioPAX della pathway
					searchHit.getChildNodes().item(2).getChildNodes().item(0).getNodeValue(),	// Nome della patway
					searchHit.getChildNodes().item(3).getChildNodes().item(0).getNodeValue(),	// Database da dove si preleva la pathway
					searchHit.getChildNodes().item(4).getChildNodes().item(0).getNodeValue())); // Organismo della pathway		
		}
	}		

	/**
	 * Metodo toString 
	 * 
	 * @return the result stringha costruita medianto l'uso dell'interatore.
	 */
	public String toString() {
		String result = "[";
		int i = 0; 
		for (i = 0; i < searchHitsInfo.size() - 1; ++i) {
			result += searchHitsInfo.get(i).toString() + ", ";
		}
		
		result += searchHitsInfo.get(i).toString() + ']';

		return result;
	}
	
	/**
	 * Metodo getter che ritorna le informazioni dell'i-esima pathway prelevate.
	 * 
	 * @param i
	 * @return
	 */
	public String elementOf(int i) {
		return searchHitsInfo.get(i).toString();
	}
	
	/**
	 * Metodo per accedere alla pathway BioPAX tramite un'indirizzo PURL.
	 * 
	 * @param bioPaxSource
	 * @return l'instanza inputStream
	 */
	private InputStream getSourceModel(String bioPaxSource) {
//		Creazione dell'oggetto URL
		try {
			url = new URL(bioPaxSource);
		} catch (MalformedURLException e2) {
			// Eccezione per il protolotto dell'URL sconosciuto
			e2.printStackTrace();
		}

//		Aprertura della connesione a URL.
//		Manipola il parametro che effettua la connessione.
		try {
			connection = url.openConnection();	
		} catch (IOException e) {
			// Eccezione in fase di connesione all'indirizzo
			e.printStackTrace();			
		}
		
		connection.setDoOutput(true);
		
//		Prendere il testo presnte nell'URI		
		InputStream inputStream = null;
		try {
			inputStream = url.openStream();
		} catch (IOException e1) {	
			// Eccezione provocata dallo stream
			e1.printStackTrace();
		}
		
		return inputStream;
	}
	
	/**
	 * Metodo per generare il modello BioPAX, passando l'URI.
	 * 
	 * @param bioPaxSource - URI
	 * @return modello BioPAX
	 */
	public Model generateBioPAXModel(String bioPaxSource) {
//		Prelevare il modello OWL dall'URL purl		
		InputStream inputStream = getSourceModel(bioPaxSource);
		
//		Cleazione del modello BioPAX		
		Model model = ioHandler.convertFromOWL(inputStream);
		
		return model;		
	}
	
	/**
	 * Metodo getter che ritono il numero di pathway.
	 * 
	 * @return numero di patthway
	 */
	public int getSize() {
		return searchHitsInfo.size();
	}
	
	/**
	 * Metodo getter per generare il modello BioPAX della pathway i-esima. 
	 * 
	 * @param index
	 * @return modello BioPAX dell'i-eima pathway
	 */
	public Model getBioPAXModel(int index) {
		return generateBioPAXModel(searchHitsInfo.get(index).getPathwayUri());
	}	
}
