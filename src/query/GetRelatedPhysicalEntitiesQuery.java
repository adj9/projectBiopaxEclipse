package query;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.PhysicalEntity;
import org.biopax.paxtools.query.QueryExecuter;

/**
 * Query che ritornana un {@link Set} gli oggetti {@link PhysicalEntity} dato un oggetto {@link BioPAXElement} correlati, solo per i BioPAX livello 3. 
 *
 * @author Alessandro
 *
 */
public class GetRelatedPhysicalEntitiesQuery implements ExsecuteQuey  {
	private BioPAXElement element;
	private Set<PhysicalEntity> pes;
	private Set<PhysicalEntity> result;
	
	public GetRelatedPhysicalEntitiesQuery(BioPAXElement element, Set<PhysicalEntity> pes) {
		this.element = element;
		this.pes = pes;
		this.result = new HashSet<PhysicalEntity>();
	}
	
	@Override
	public void exsecuteQuery() {
		// TODO Auto-generated method stub
		this.result = QueryExecuter.getRelatedPhysicalEntities(element, pes);
	}

	@Override
	public void saveQuery() {
		// TODO Auto-generated method stub
		SimpleIOHandler handler = new SimpleIOHandler();
		Model model = null;
		// Creare il modello da result.
		try {
			handler.convertToOWL(model, new FileOutputStream(System.getProperty("user.dir") 
																		+ "Pathways\\QueryHrev1.owl"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the result
	 */
	public Set<PhysicalEntity> getResult() {
		return result;
	}
}
