package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.biopax.paxtools.impl.level3.BiochemicalPathwayStepImpl;
// import org.biopax.paxtools.model.BioPAXElement;



import org.biopax.paxtools.model.BioPAXElement;
// import org.biopax.paxtools.model.level3.PhysicalEntity;


import org.biopax.paxtools.model.level3.PhysicalEntity;

import query.GetRelatedPhysicalEntitiesQuery;
import query.Pathway;

public class GetRelatedPhysicalEntitiesQueryTest {
	public static void main(String[] args) {
		Pathway hrev1 = new Pathway("REACT_8.2", "hrev1");
		try {
			FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\Pathways\\hrev1.owl");
			hrev1.generateModel(inputStream);
		} catch(FileNotFoundException e) {
			System.out.println("Exc!");
			e.printStackTrace();
		}
		
		Set<BioPAXElement> physicalEntity = new HashSet<BioPAXElement>();
		physicalEntity = hrev1.getModel().getObjects();
		System.out.println("Generate BioPAXElements");
		System.out.println("physicalEntity is empty? " + physicalEntity.isEmpty());
		Iterator<BioPAXElement> it = physicalEntity.iterator();
		System.out.println("THE ELEMENTS ARE:");
		/* 
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		*/
		System.out.println();
		Set<PhysicalEntity> pes = new HashSet();
		pes.add((PhysicalEntity) it.next());
		System.out.println("pes il empty? " + pes.isEmpty());
		GetRelatedPhysicalEntitiesQuery query = new GetRelatedPhysicalEntitiesQuery(new BiochemicalPathwayStepImpl(), 
																					null);
		query.exsecuteQuery();
		System.out.println("Ok!");
		
		System.out.println("Query is empty? " + query.getResult().isEmpty());
	}
}

