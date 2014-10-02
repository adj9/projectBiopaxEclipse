package cpath2API;


// Classi per la scrittura del risultato della query
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

// Classe per la connessione all'URL per prelevate un'istanza di tipo InputStream 
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// Classi della libreria Paxtools, per generare un modello BioPAX
import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;

// Classi per generare un'albero DOM da un documento XML
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
// org.w3c.dom ---> Per manipolare un'oggetto DOM.
// 				    Document rappresenta la radice del DOM.
import org.w3c.dom.Document;
// Classe che gestisce l'eccezione per la creazione dell'istanza Document
import org.xml.sax.SAXException;		


/**
 * Classe per effettuare la connessione a cPath2.
 * Questa classe effettua query applicando l'architetture REST al sito http://www.pathwaycommons.org/pc2/.
 * Su questo sito si concatena la parte dei parametri per la query.  
 * 
 * @author Alessandro
 *
 */
public class CPathConnection {	
//	Istanziazione degli oggetti per il collegamento all'URL	
	private URL url;
	private URLConnection connection;

//	Sito base per la connessione	
	protected final static String URL_BASE_POINT = "http://www.pathwaycommons.org/pc2/";

//	Instanze che descrivono informazioni dell'URI da realizzare	
	protected String command;
	protected String query; 

//	Instaziazione delloggetto per la creazione del modello BioPAX	
	private BioPAXIOHandler ioHandler;

//	Attributi di classe per costruire l'albero DOM
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;
	
	/**
	 * Costruttore di classe.
	 * 
	 * @param command - nome del comendo di Pathway Commons 
	 */
	public CPathConnection(String command) {
		this.url = null;
		this.connection = null;
		//this.directory = null;
		this.command = command;
		this.query = null;
		
		this.ioHandler  = new SimpleIOHandler();
	}		

	/**
	 * Metodo che genera un'instanza {@link InputStream}, passando l'URI valido del modello OWL.
	 *  
	 * @param bioPaxSource - parte dell'URL con i parametri della query
	 * @return un'istranza di tipo InputStream con il risultato della query 
	 */
	protected InputStream getInputStream(String bioPaxSource) {		
//		Creazione dell'istanza URL 		
		try {
			url = new URL(URL_BASE_POINT + bioPaxSource);
		} catch (MalformedURLException e) {
//			Eccezione per il protolotto dell'URL sconosciuto
			e.printStackTrace();
		}
		
//		Apertura della connesione all'URL		
		try {
			connection = url.openConnection();	
		} catch (IOException e) {
//			Eccezione in fase di connesione all'indirizzo
			e.printStackTrace();			
		}
		
//		Accedo all'output per la scritture usando un InputStreamReader		
		connection.setDoOutput(true);		
		
//		Creazione dell'istanza InputStrwam con il testo della query, tramitte l'istanza url 		
		InputStream inputStream = null;
		try {
			inputStream = url.openStream();
		} catch (IOException e) {
//			Eccezione provocata dallo stream
			e.printStackTrace();
		}
		
		return inputStream;
	}
	
	/**
	 * Metodo per generare il modello BioPAX, tramite un'URI valido del modello OWL.
	 * 
	 * @param bioPaxSource - URI che contiene il codice OWL della pathway BioPAX 
	 * @return modello BioPAX 
	 */
	public Model generateBioPAXModel(String bioPaxSource) {		
//		Creazione dell'oggetto Model tramite lo stream che contiene il codice OWL della pathway BioPAX
		return ioHandler.convertFromOWL(getInputStream(bioPaxSource));
	}

	protected void getSource(String query, OutputStream outputStream) {
		BufferedReader buffer = new BufferedReader(new InputStreamReader(getInputStream(query)));
		
//		Stream per la stampa del risultato 		
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		
		String line = null;
		while (true) {
			try {
				line = buffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (line != null ) {
				try {
					writer.write(line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		
		try {
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generazione dell'albero DOM della risorsa XML ad un url di Pathway Commons.
	 * 
	 * @param query la query con i parametri
	 * @return
	 */
	protected void createDOMTree(String query) {
		this.query = query;
		
//		Creazione del documento		
//		Abilito l'utilizzo dell'albero DOM		
		factory = DocumentBuilderFactory.newInstance();
		System.out.println("Valid: " + factory.isValidating());
//		L'XML è bel validante 		
		//facory.setValidating(false);		
		
//		Classe che restiotuisce un oggetto Document. 
//		Domunent rappresenta un DOM. 		
		builder = null;
		try {
			System.out.println("CREARE IL DOCUMENTO");
			builder = factory.newDocumentBuilder();
			System.out.println("CREATO IL DOCUMENTO");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println("ParserConfigurationException exception!");
			e.printStackTrace();
		}
		System.out.println("SONO FUORI DAL try-catch DI newDocumentBuilder()");
//		Effettuo il parsing del documento XML		
		try {
//			Validazione del documento e generazioe della struttura DOM
			System.out.println("STO FACENDO IL PARSE");
			//String url = ;
			document = builder.parse(URL_BASE_POINT + this.query);			
		} catch (SAXException e) {
			// Eccezine correlata al SAX
			e.printStackTrace();
		} catch (IOException e) {
			// Eccezione di I/O della risorsa XML
			e.printStackTrace();
		}
	}		
	
	/**
	 * Metodo getter per restituire l'albero DOT del parsing di un XML.
	 * 
	 * @return the document
	 */
	public Document getDocument() {
		createDOMTree(query);
		
		return document;
	}
	
	/**
	 * Metodo getter del nome del comando.
	 * 
	 * @return the command
	 */
	protected String getCommand() {
		return command;
	}	
}
