package demo;

import java.util.Iterator;
import java.util.Set;

import org.biopax.paxtools.model.BioPAXFactory;
import org.biopax.paxtools.model.BioPAXLevel;
import org.biopax.paxtools.model.Model;
import org.biopax.paxtools.model.level3.Protein;
import org.biopax.paxtools.model.level3.Provenance;

public class Factory {
	public static void main(String[] args) {
		BioPAXFactory factory = BioPAXLevel.L3.getDefaultFactory();
		Model model = factory.createModel();
		model.setXmlBase("http://biopax.org/tutorial/");
		Protein protein1 = model.addNew(Protein.class, "http://biopax.org/tutorial/test1"); 
		Set<Provenance> set = protein1.getDataSource();
		Iterator<Provenance> it = set.iterator();
		/*
		System.out.println("set is empty? " + set.isEmpty());
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		*/
		Protein idProtein = (Protein) model.getByID("http://biopax.org/tutorial/test1");
		System.out.println("\nok!");
	}
}
