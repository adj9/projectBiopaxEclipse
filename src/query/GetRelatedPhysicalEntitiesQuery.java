package query;

import java.util.HashSet;
import java.util.Set;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.PhysicalEntity;
import org.biopax.paxtools.query.QueryExecuter;

/**
 * Query che dato un oggetto {@link BioPAXElement} ritornano gli oggetti {@link PhysicalEntity} correlati, per i BioPAX livello 3. 
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
		
	}

	
}
