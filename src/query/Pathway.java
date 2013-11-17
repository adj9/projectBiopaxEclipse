package query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.biopax.paxtools.io.BioPAXIOHandler;
import org.biopax.paxtools.io.SimpleIOHandler;
// import org.biopax.paxtools.io.jena.JenaIOHandler;
import org.biopax.paxtools.model.Model;

public class Pathway {
	private String id;
	private String name;
	private Model model; 
	private BioPAXIOHandler handler;
	
	public Pathway(String id, String name) {
		this.id = id;
		this.name = name;
		this.model = null;
		this.handler = new SimpleIOHandler();
	}
	
	/**
	 * Metodo per generare il modello dal file XML della pathway BioPAX.
	 * 
	 * @param inputStream
	 * @throws FileNotFoundException
	 */
	public void generateModel(FileInputStream inputStream) throws FileNotFoundException {
		this.model = handler.convertFromOWL(inputStream);
	}
	
	/**
	 * Metedo getter del livello del modello BioPAX.
	 * 
	 * @return this.model.getLevel().getFilename()
	 */
	public String getLevel() {
		return this.model.getLevel().getFilename();
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}	
}
