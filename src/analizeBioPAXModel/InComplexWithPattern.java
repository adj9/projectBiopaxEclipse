package analizeBioPAXModel;


// Classe per il tipo di ritorno del metodo exesecutePattern
import java.util.List;

// Classe della libreria Paxtools, per passare il modello BioPAX al metodo exesecutePattern()
import org.biopax.paxtools.model.Model;

// Classi della libreria biopax-pattern
import org.biopax.paxtools.pattern.Match;
import org.biopax.paxtools.pattern.PatternBox;
import org.biopax.paxtools.pattern.miner.SIFEnum;


/**
 * Classe per implementre il pattern inComplexWith della libreria biopax-pattern.
 * Questa classe estende {@link ExecutePattern}.
 * 
 * @author Alessandro
 *
 */
public class InComplexWithPattern extends ExecutePattern {		
	
	/**
	 * Costruttore di default per caratterizzare il tipo di relazione delle interazioni. 
	 */
	public InComplexWithPattern() {
		super(SIFEnum.IN_COMPLEX_WITH.getTag());
	}

	@Override
	public List<Match> executePattern(Model model, String entityName) {				
		return this.executePattern(model, entityName, PatternBox.inComplexWith(), "Protein 1");
	}
}
