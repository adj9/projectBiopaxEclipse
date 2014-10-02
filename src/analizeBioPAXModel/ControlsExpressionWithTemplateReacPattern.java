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
 * Classe per implementre il pattern controlsExpressionWithTemplateReac della libreria biopax-pattern.
 * Questa classe estende {@link ExecutePattern}.
 * 
 * @author Alessandro
 *
 */
public class ControlsExpressionWithTemplateReacPattern extends ExecutePattern {
	
	/**
	 * Costruttore di default per caratterizzare il tipo di relazione delle interazioni. 
	 */	
	public ControlsExpressionWithTemplateReacPattern() {
		super(SIFEnum.CONTROLS_EXPRESSION_OF.getTag());
	}

	@Override
	public List<Match> executePattern(Model model, String entityName) {		
		return super.executePattern(model, 
									entityName, 
									PatternBox.controlsExpressionWithTemplateReac(), 
									"TF PR");
	}
}
