package ui;


// Classi per generare i file sif di output
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// Classi della libreria Paxtools, per generare un modello BioPAX
import org.biopax.paxtools.model.Model;

//Classi della libreria biopax-pattern, per il riisultato della ricerca del pattern nel modello
import org.biopax.paxtools.pattern.Match;

// Classi del package "analizeBioPAXModel" per eseguire i pattern
import analizeBioPAXModel.ControlsExpressionWithConversionPattern;
import analizeBioPAXModel.ControlsExpressionWithTemplateReacPattern;
import analizeBioPAXModel.ExecutePattern;
import analizeBioPAXModel.InComplexWithPattern;
import analizeBioPAXModel.MolecularInteractionPattern;

// Classi del package "cpath2API" per generare i modelli BioPAX
import cpath2API.GraphCommand;
import cpath2API.GraphCommand.GENERATE_MODEL_KIND;


/**
 * Classe per eseguire il programma su linea di comandi.
 * Il comando ha la seguente strutture: <GENE1> <GENE2> ... <GENEn> - <PATTERN1> <PATTRN2> ... <PATTERNn>
 *   
 * @author Alessandro
 *
 */
public class PatternProcess {
	private String[] entityName;
	private String[] patternName;
	
//	Stanza del comando GRAPH di Pathway Commons per generare i modell BioPAX	
	private GraphCommand graph; 

	/**
	 * Costtruttore.
	 * 
	 * @param entityName - array dei nomi delle entità
	 * @param patternName - array dei nomi dei pattern
	 */
	public PatternProcess(String[] entityName, String[] patternName) {
		this.entityName = entityName;
		this.patternName = patternName;
				
		this.graph = new GraphCommand();
	}	
	
	/**
	 * Enumeratore dei pattern supportati.
	 * 
	 * @author Alessandro
	 */
	public enum PATTERN_TYPE {
		IN_COMPLEX_WITH("IN_COMPLEX_WITH"),
		MOLECULAR_INTERACTION("MOLECULAR_INTERACTION"),
		CONTROLS_EXPRESSION_WITH_TEMPLATE_REACT("CONTROLS_EXPRESSION_WITH_TEMPLATE_REACT"),
		CONTROLS_EXPRESSION_WITH_CONVERSION("CONTROLS_EXPRESSION_WITH_CONVERSION");
		
		private String patternName;
		
		PATTERN_TYPE(String patternName) {
			this.patternName = patternName;
		}

		/**
		 * @return the patternName
		 */
		protected String getPatternName() {
			return patternName;
		}
	}
	
	/**
	 * Metodo per eseguire la ricerca sul modello BioPAX.
	 * 
	 * @param pattern - istanza del pattern
	 * @param patternType - tipo del pattern per daree il nome al fila
	 * @param model - modello BioPAX
	 */
	private void executePattern(ExecutePattern pattern, PATTERN_TYPE patternType, Model[] model) {
//		Ciclo for sull'array dei nomi dei pattern		
		for (int i = 0; i < entityName.length; ++i) {
//			Ricerca del pattern nel modello BioPAX					
			List<Match> patternMatch = pattern.executePattern(model[i], entityName[i]);
						
//			Istanziazione dell'oggetto per scrivere il risultato della ricerca sul pattern			
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(
						new File(System.getProperty("user.dir") 
									+ "/" + entityName[i] + '_' + patternType.name() + ".sif"));
			} catch (FileNotFoundException e) {
				// Eccezione per l'esistenza del file
				e.printStackTrace();
			}

//			Scrittura della ricerca sul file	
			try {
				pattern.writeResultAsSIF(outputStream, entityName[i]);
			} catch (IOException e) {
				// Eccezione per la scrittura su file
				e.printStackTrace();
			}
		}//	Fine ciclo	--- sui nomi delle entità
	}
	
	/**
	 * Metodo di avvio del programma.
	 */
	public void starter() {
		int i = 0;
		
//		Generazione dei modelli BioPAX con il comando GRAPG di cPath2		
		Model[] model = new Model[entityName.length];
		for (i = 0; i < model.length; ++i) {
			model[i] = graph.getModelOfEntity(entityName[i], GENERATE_MODEL_KIND.NEIGHBORHOOD);
		}
		
//		Esecuzione dei patten con due cari: 										
		for (i = 0; i < patternName.length; ++i) {
//				Controllo del nome del pattern per l'esecuzione della ricerca				
			if (patternName[i].equals(PATTERN_TYPE.IN_COMPLEX_WITH.name())) {
				this.executePattern(new InComplexWithPattern(), 
									PATTERN_TYPE.IN_COMPLEX_WITH,
									model);
			} else if (patternName[i].equals(PATTERN_TYPE.MOLECULAR_INTERACTION.name()) ) {
				this.executePattern(new MolecularInteractionPattern(), 
						            PATTERN_TYPE.MOLECULAR_INTERACTION,
						            model);
			} else if (patternName[i].equals(PATTERN_TYPE.CONTROLS_EXPRESSION_WITH_TEMPLATE_REACT.name())) {
				this.executePattern(new ControlsExpressionWithTemplateReacPattern(), 
						            PATTERN_TYPE.CONTROLS_EXPRESSION_WITH_TEMPLATE_REACT,
						            model);
			} else if (patternName[i].equals(PATTERN_TYPE.CONTROLS_EXPRESSION_WITH_CONVERSION.name())) {
				this.executePattern(new ControlsExpressionWithConversionPattern(), 
						            PATTERN_TYPE.CONTROLS_EXPRESSION_WITH_CONVERSION,
						            model);
			} else {
//					Caso in cui il nome del pattern è errato 					
				System.err.println("ERROR");
			}
		}			
	}
}
