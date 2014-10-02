package cpath2API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Classi della libreria JSON.org per la gestione del risultato della query 
import org.json.JSONException;
import org.json.JSONObject;


public class IDMappingCommand extends CPathConnection{
	private static final String URI_UNIPROT_ENTITY = "http://identifiers.org/uniprot";
	
	public IDMappingCommand() {
		super("idmapping");
	}

	public String getIDOf(String entityName) {	
		BufferedReader buffer = 
				new BufferedReader(
						new InputStreamReader(
								getInputStream(this.command + "?id=" + entityName)));
		String line = null;
		try {
			line = buffer.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		Instanziazione dell'oggetto JSONObjec per poter prevedere il valore della chiave entity		
		JSONObject obj = null;
		try {
			obj = new JSONObject(line);
		} catch (JSONException e1) {
			// Gestione dell'eccezione JSONException
			e1.printStackTrace();
		}	

//		Prelevazione del valore
		String res = null;
		try {
			res = obj.getString(entityName);
		} catch (JSONException e) {
			// Gestione dell'eccezione JSONException
			e.printStackTrace();
		}
		
		return res;
	}

	public String getUniProtUriOf(String entityName) {
		return URI_UNIPROT_ENTITY + "/" + getIDOf(entityName);
	}	
}
