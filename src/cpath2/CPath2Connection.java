package cpath2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.hp.hpl.jena.sparql.pfunction.library.textMatch;

/**
 * Classe per effettuare la connessione a cPath2 
 * 
 * @author Alessandro
 *
 */
public class CPath2Connection {
	private static String URL_BASE_POINT = " http://www.pathwaycommons.org//pc2";
	private URL url;
	private URLConnection connection;
	
	
	public CPath2Connection() {
		try {
			this.url = new URL(URL_BASE_POINT);
		} catch (MalformedURLException e) {
			// Eccezione per il protolotto dell'URL sconosciuto 
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the connection
	 */
	public URLConnection getConnection() {
		return connection;
	}

	public void read() {
		System.out.println("*READ*");
		URL urlGet = null; 
		try {
			urlGet = new URL(URL_BASE_POINT + "/search.xml?q=Q06609");
		} catch (MalformedURLException e2) {
			// Eccezione per il protolotto dell'URL sconosciuto 
			e2.printStackTrace();
		}
		try {
			connection = urlGet.openConnection();	// Apro la connesione a urlGet
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("*NON E' AVVENUTA LA CONNESSIONE*");
			e.printStackTrace();			
		}
		
		System.out.println("*CONNESSIONE AVVENUTA*");
		connection.setDoOutput(true);		// Accedo all'output per la scritture usando un InputStreamReader
		System.out.println("*APERTURA DELL'OUTPUT*");
		
		InputStream inputStream = null;
		try {
			inputStream = urlGet.openStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		Scrittura sul file Protein.xml
		File file = new File(System.getProperty("user.dir") + "\\Protein.xml");	// Creare il file
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PrintStream print = null;		// Stream per la stampe
		try {
			print = new PrintStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader buffer = null;
		buffer = new BufferedReader(new InputStreamReader(inputStream));
		
		try {
			print.print(buffer.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		print.close();					// Ciusura dello stream 
	}
	
	public static void main(String[] args) {
		CPath2Connection cPath2 = new CPath2Connection();
		
		cPath2.read();
		
		System.out.println("OK!");
	}
}
